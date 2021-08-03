package io.gaia_app.runner;

import io.gaia_app.credentials.Credentials;
import io.gaia_app.credentials.CredentialsService;
import io.gaia_app.modules.bo.TerraformImage;
import io.gaia_app.settings.bo.Settings;
import io.gaia_app.stacks.bo.*;
import io.gaia_app.stacks.repository.JobRepository;
import io.gaia_app.stacks.repository.StackRepository;
import io.gaia_app.stacks.repository.StepRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RunnerControllerTest {

    @InjectMocks
    private RunnerController runnerController;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private RunnerCommandBuilder runnerCommandBuilder;

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private Settings settings;

    private Step step;

    private Job job;

    private Stack stack;

    @BeforeEach
    void setUp() {
        this.job = new Job();
        this.job.setStatus(JobStatus.PLAN_PENDING);
        this.job.setStackId("fakeStackId");
        this.job.setSteps(List.of(new Step(), new Step()));
        this.job.setTerraformImage(new TerraformImage("hashicorp/terraform", "0.13.0"));

        this.step = new Step();
        this.step.setId("fakeStepId");
        this.step.setStatus(StepStatus.PENDING);
        this.step.setType(StepType.PLAN);
        this.step.setJobId("fakeJobId");

        when(stepRepository.findFirstByStatus(StepStatus.PENDING)).thenReturn(Optional.of(step));

        when(jobRepository.findById("fakeJobId")).thenReturn(Optional.of(job));

        this.stack = new Stack();
        when(stackRepository.findById("fakeStackId")).thenReturn(Optional.of(stack));

        lenient().when(runnerCommandBuilder.buildPlanScript(job, stack, null)).thenReturn("plan script");
        lenient().when(runnerCommandBuilder.buildApplyScript(job, stack, null)).thenReturn("apply script");
        lenient().when(runnerCommandBuilder.buildPlanDestroyScript(job, stack, null)).thenReturn("plan destroy script");
        lenient().when(runnerCommandBuilder.buildDestroyScript(job, stack, null)).thenReturn("apply destroy script");
    }

    @Test
    void findFirstRunnableStep_shouldUsePlanScript() {
        // given
        this.job.setType(JobType.RUN);
        this.job.setStatus(JobStatus.PLAN_PENDING);
        this.job.getSteps().get(0).setType(StepType.PLAN);

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        verify(runnerCommandBuilder).buildPlanScript(job, stack, null);

        assertThat(result)
            .containsEntry("script", "plan script");
    }

    @Test
    void findFirstRunnableStep_shouldUseApplyScript() {
        // given
        this.job.setType(JobType.RUN);
        this.job.setStatus(JobStatus.APPLY_PENDING);
        this.step.setType(StepType.APPLY);

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        verify(runnerCommandBuilder).buildApplyScript(job, stack, null);

        assertThat(result)
            .containsEntry("script", "apply script");
    }

    @Test
    void findFirstRunnableStep_shouldUsePlanDestroyScript() {
        // given
        this.job.setType(JobType.DESTROY);
        this.job.setStatus(JobStatus.PLAN_PENDING);

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        verify(runnerCommandBuilder).buildPlanDestroyScript(job, stack, null);

        assertThat(result)
            .containsEntry("script", "plan destroy script");
    }

    @Test
    void findFirstRunnableStep_shouldUseApplyDestroyScript() {
        // given
        this.job.setType(JobType.DESTROY);
        this.job.setStatus(JobStatus.APPLY_PENDING);
        this.step.setType(StepType.APPLY);

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        verify(runnerCommandBuilder).buildDestroyScript(job, stack, null);

        assertThat(result)
            .containsEntry("script", "apply destroy script");
    }

    @Test
    void findFirstRunnableStep_shouldUsePlanStep() {
        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        assertThat(result)
            .containsEntry("id", "fakeStepId");
    }

    @Test
    void findFirstRunnableStep_shouldUseStackImage() {
        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        assertThat(result)
            .containsEntry("image", "hashicorp/terraform:0.13.0");
    }

    @Test
    void findFirstRunnableStep_shouldImportStackCredentials() {
        //given
        stack.setCredentialsId("fakeCredentials");

        var credentials = mock(Credentials.class);
        when(credentialsService.load("fakeCredentials")).thenReturn(Optional.of(credentials));
        when(credentials.toEnv()).thenReturn(List.of("access_key=value","secret_key=secretValue"));

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        assertThat(result)
            .containsEntry("env", credentials.toEnv());
    }

    @Test
    void findFirstRunnableStep_shouldImportSettingsEnvVars() {
        //given
        when(settings.env()).thenReturn(List.of("LED=ZEPPELIN", "THE=DOORS"));

        // when
        var result = runnerController.findFirstRunnableStep();

        // then
        assertThat(result)
            .containsEntry("env", List.of("LED=ZEPPELIN", "THE=DOORS"));
    }

}
