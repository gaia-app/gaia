package io.gaia_app.runner;

import com.jayway.jsonpath.JsonPath;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.StepRepository;
import io.gaia_app.test.SharedMongoContainerTest;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RunnerControllerIT extends SharedMongoContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private Settings settings;

    @BeforeEach
    void setUp() {
        mongo.emptyDatabase();
        mongo.runScript("00_organization.js");
        mongo.runScript("10_user.js");
        mongo.runScript("20_module.js");
        mongo.runScript("30_stack.js");
    }


    @Test
    @WithMockUser("gaia-runner")
    void findFirstRunnableJob_shouldReturnNothing_whenNoJobIsPending() throws Exception {
        mockMvc.perform(get("/api/runner/steps/request"))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser("gaia-runner")
    void findFirstRunnableJob_shouldReturnNothing_whenJobIsCreatedButNotPending() throws Exception {
        var stackId = "5a215b6b-fe53-4afa-85f0-a10175a7f264";

        // creating a pending job of type RUN
        mockMvc.perform(post("/api/stacks/{stackId}/RUN", stackId).with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jobId", notNullValue()));

        mockMvc.perform(get("/api/runner/steps/request"))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser("gaia-runner")
    void findFirstRunnableJob_shouldReturnAJob_whenJobIsPending() throws Exception {
        var stackId = "5a215b6b-fe53-4afa-85f0-a10175a7f264";

        // creating a job of type RUN
        var result = mockMvc.perform(post("/api/stacks/{stackId}/RUN", stackId).with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jobId", notNullValue()))
            .andReturn();
        var jobId = JsonPath.read(result.getResponse().getContentAsString(), "$.jobId");

        // calling 'plan' to make it pending
        mockMvc.perform(post("/api/jobs/{jobId}/plan", jobId).with(csrf()))
            .andExpect(status().isOk());

        // change settings to add some env_var
        var envVar = new Settings.EnvVar();
        envVar.setName("LONDON");
        envVar.setValue("CALLING");
        settings.getEnvVars().add(envVar);

        // request as the runner will do
        mockMvc.perform(get("/api/runner/steps/request"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.env", hasItems("LONDON=CALLING")))
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.script", notNullValue()))
            .andExpect(jsonPath("$.image", is("hashicorp/terraform:0.11.14")));
    }

    @Test
    @WithMockUser("gaia-runner")
    void updateLogs_shouldAppendLogsToStep() throws Exception {
        var step = new Step(StepType.APPLY, "fakeJobId");
        step.setLogs(List.of("first log line"));
        this.stepRepository.save(step);

        mockMvc.perform(
            put("/api/runner/steps/{stepId}/logs", step.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("second log line"))
            .andExpect(status().isOk())
            .andReturn();

        var updatedStep = this.stepRepository.findById(step.getId()).orElseThrow();
        assertThat(updatedStep.getLogs())
            .hasSize(2)
            .containsExactly("first log line", "second log line");
    }

    @Test
    @WithMockUser("gaia-runner")
    void startStep_shouldUpdateStepState() throws Exception {
        var step = new Step(StepType.APPLY, "fakeJobId");

        var job = new Job();
        job.setId("fakeJobId");
        job.setStatus(JobStatus.PLAN_PENDING);
        job.setSteps(List.of(step));

        this.stepRepository.save(step);
        this.jobRepository.save(job);

        mockMvc.perform(
            put("/api/runner/steps/{stepId}/start", step.getId())
                .with(csrf()))
            .andExpect(status().isOk())
            .andReturn();

        var updatedStep = this.stepRepository.findById(step.getId()).orElseThrow();
        assertThat(updatedStep.getStatus()).isEqualTo(StepStatus.STARTED);

        var updatedJob = this.jobRepository.findById("fakeJobId").orElseThrow();
        assertThat(updatedJob.getStatus()).isEqualTo(JobStatus.PLAN_STARTED);
    }

    @Test
    @WithMockUser("gaia-runner")
    void updateStepStatus_shouldUpdateStepState_inCaseOfSuccess() throws Exception {
        var stack = new Stack();
        stack.setId("fakeStack");

        var step = new Step(StepType.APPLY, "fakeJobId");
        step.setStartDateTime(LocalDateTime.now());
        step.setStatus(StepStatus.STARTED);

        var job = new Job();
        job.setId("fakeJobId");
        job.setStackId("fakeStack");
        job.setStatus(JobStatus.PLAN_STARTED);
        job.setSteps(List.of(step));

        this.stackRepository.save(stack);
        this.jobRepository.save(job);
        this.stepRepository.save(step);

        mockMvc.perform(
            put("/api/runner/steps/{stepId}/end", step.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("0"))
            .andExpect(status().isOk())
            .andReturn();

        var updatedStep = this.stepRepository.findById(step.getId()).orElseThrow();
        assertThat(updatedStep.getStatus()).isEqualTo(StepStatus.FINISHED);

        var updatedJob = this.jobRepository.findById("fakeJobId").orElseThrow();
        assertThat(updatedJob.getStatus()).isEqualTo(JobStatus.PLAN_FINISHED);
    }

    @Test
    @WithMockUser("gaia-runner")
    void updateStepStatus_shouldUpdateStackStateToRunning_inCaseOfSuccessfulApply() throws Exception {
        var stack = new Stack();
        stack.setId("fakeStackId");

        var planStep = new Step(StepType.PLAN, "fakeJobId");
        planStep.setStatus(StepStatus.FINISHED);

        var applyStep = new Step(StepType.APPLY, "fakeJobId");
        applyStep.setStartDateTime(LocalDateTime.now());
        applyStep.setStatus(StepStatus.STARTED);

        var job = new Job();
        job.setId("fakeJobId");
        job.setStackId("fakeStackId");
        job.setType(JobType.RUN);
        job.setStatus(JobStatus.APPLY_STARTED);
        job.setSteps(List.of(planStep, applyStep));

        this.stackRepository.save(stack);
        this.jobRepository.save(job);
        this.stepRepository.saveAll(job.getSteps());

        mockMvc.perform(
            put("/api/runner/steps/{stepId}/end", planStep.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("0"))
            .andExpect(status().isOk())
            .andReturn();

        var updatedStack = this.stackRepository.findById("fakeStackId").orElseThrow();
        assertThat(updatedStack.getState()).isEqualTo(StackState.RUNNING);
    }

    @Test
    @WithMockUser("gaia-runner")
    void updateStepStatus_shouldUpdateStackStateToStopped_inCaseOfSuccessfulApply() throws Exception {
        var stack = new Stack();
        stack.setState(StackState.RUNNING);
        stack.setId("fakeStackId");

        var planStep = new Step(StepType.PLAN, "fakeJobId");
        planStep.setStatus(StepStatus.FINISHED);

        var applyStep = new Step(StepType.APPLY, "fakeJobId");
        applyStep.setStartDateTime(LocalDateTime.now());
        applyStep.setStatus(StepStatus.STARTED);

        var job = new Job();
        job.setId("fakeJobId");
        job.setStackId("fakeStackId");
        job.setType(JobType.DESTROY);
        job.setStatus(JobStatus.APPLY_STARTED);
        job.setSteps(List.of(planStep, applyStep));

        this.stackRepository.save(stack);
        this.jobRepository.save(job);
        this.stepRepository.saveAll(job.getSteps());

        mockMvc.perform(
            put("/api/runner/steps/{stepId}/end", planStep.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("0"))
            .andExpect(status().isOk())
            .andReturn();

        var updatedStack = this.stackRepository.findById("fakeStackId").orElseThrow();
        assertThat(updatedStack.getState()).isEqualTo(StackState.STOPPED);
    }

    @Test
    @WithMockUser("gaia-runner")
    void uploadPlan_shouldUpdateTheJob_withThePlanData() throws Exception {
        // given
        var planStep = new Step(StepType.PLAN, "fakeJobId");
        planStep.setStatus(StepStatus.FINISHED);

        var job = new Job();
        job.setId("fakeJobId");
        job.setStackId("fakeStackId");
        job.setType(JobType.DESTROY);
        job.setStatus(JobStatus.APPLY_STARTED);
        job.setSteps(List.of(planStep));

        this.jobRepository.save(job);
        this.stepRepository.saveAll(job.getSteps());

        // when
        String planContent = Files.contentOf(new ClassPathResource("sample-plan.json").getFile(), StandardCharsets.UTF_8);
        mockMvc.perform(
            post("/api/runner/stacks/{stackId}/jobs/{jobId}/plan", "stackId", job.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(planContent))
            .andExpect(status().isOk())
            .andReturn();

        // then
        var savedStep = stepRepository.findById(planStep.getId());
        assertThat(savedStep).isPresent();
        assertThat(savedStep.get().getPlan()).satisfies(it -> {
            assertThat(it.getCreateCount()).isEqualTo(2);
            assertThat(it.getUpdateCount()).isEqualTo(1);
            assertThat(it.getDeleteCount()).isEqualTo(1);
            assertThat(it.getNoOpCount()).isEqualTo(1);
        });
    }
}
