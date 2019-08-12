package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import io.codeka.gaia.bo.*;
import io.codeka.gaia.modules.bo.TerraformModule;
import io.codeka.gaia.stacks.repository.JobRepository;
import io.codeka.gaia.stacks.repository.StackRepository;
import io.codeka.gaia.stacks.bo.*;
import io.codeka.gaia.teams.bo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StackRunnerTest {

    @Mock
    private DockerClient dockerClient;

    @Mock(answer = Answers.RETURNS_SELF)
    private ContainerConfig.Builder builder;

    @Mock
    private Settings settings;

    @Mock
    private StackCommandBuilder stackCommandBuilder;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private HttpHijackWorkaround httpHijackWorkaround;

    @Mock
    private JobRepository jobRepository;

    private Job job;

    private TerraformModule module;

    private Stack stack;

    @InjectMocks
    private StackRunner stackRunner;

    @BeforeEach
    void setUp() throws Exception {
        // simulating a container with id 12
        var containerCreation = mock(ContainerCreation.class);
        when(containerCreation.id()).thenReturn("12");
        when(dockerClient.createContainer(any())).thenReturn(containerCreation);

        stack = new Stack();
        job = new Job(new User());
        module = new TerraformModule();
    }

    void httpHijackWorkaroundMock() throws Exception {
        // setting mocks to let test pass till the end
        var writableByteChannel = mock(OutputStream.class);
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(writableByteChannel);
    }

    void containerExitMock(Long statusCode) throws Exception {
        // given
        var containerExit = mock(ContainerExit.class);
        when(containerExit.statusCode()).thenReturn(statusCode);
        when(dockerClient.waitContainer("12")).thenReturn(containerExit);
    }

    @Test
    void job_shouldBeSavedToDatabaseAfterRun() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // when
        stackRunner.apply(job, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);
    }

    @Test
    void successfullJob_shouldSetTheStackStateToRunning() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // when
        stackRunner.apply(job, module, stack);

        // then
        assertEquals(StackState.RUNNING, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void plan_shouldUpdateTheStackState_whenThereIsADiffForRunningStacks() throws Exception {
        // given
        stack.setState(StackState.RUNNING);

        httpHijackWorkaroundMock();
        containerExitMock(2L);
        when(stackCommandBuilder.buildPlanScript(stack, module)).thenReturn("");

        // when
        stackRunner.plan(job, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);

        assertEquals(StackState.TO_UPDATE, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void plan_shouldNotUpdateTheStackState_whenThereIsADiffForNewStacks() throws Exception {
        // given
        stack.setState(StackState.NEW);

        httpHijackWorkaroundMock();
        containerExitMock(2L);
        when(stackCommandBuilder.buildPlanScript(stack, module)).thenReturn("");

        // when
        stackRunner.plan(job, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);

        assertEquals(StackState.NEW, stack.getState());
        verifyZeroInteractions(stackRepository);
    }

    @Test
    void stop_shouldUpdateTheStackState_whenSuccessful() throws Exception {
        // given
        stack.setState(StackState.RUNNING);

        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildDestroyScript(stack, module)).thenReturn("");

        // when
        stackRunner.stop(job, module, stack);

        // then
        verify(jobRepository, times(2)).save(job);

        assertEquals(StackState.STOPPED, stack.getState());
        verify(stackRepository).save(stack);
    }

    @Test
    void jobShouldFail_whenFailingToStartContainer() throws Exception {
        // given
        stack.setState(StackState.RUNNING);

        var stackRunner = new StackRunner(dockerClient, builder, settings, stackCommandBuilder, stackRepository, httpHijackWorkaround, jobRepository);

        doThrow(new DockerException("test")).when(dockerClient).startContainer("12");
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // when
        stackRunner.apply(job, module, stack);

        // then
        assertEquals(JobStatus.FAILED, job.getStatus());
        verify(jobRepository, times(2)).save(job);
    }

    @Test
    void plan_shouldStartPreviewJob() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildPlanScript(stack, module)).thenReturn("");

        // when
        stackRunner.plan(job, module, stack);

        // then
        assertEquals(JobType.PREVIEW, job.getType());
    }

    @Test
    void apply_shouldStartRunob() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // when
        stackRunner.apply(job, module, stack);

        // then
        assertEquals(JobType.RUN, job.getType());
    }

    @Test
    void stop_shouldStartStopJob() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildDestroyScript(stack, module)).thenReturn("");

        // when
        stackRunner.stop(job, module, stack);

        // then
        assertEquals(JobType.STOP, job.getType());
    }

    @Test
    void plan_shouldConsiderModuleCLIVersion() throws Exception {
        // given
        module.setCliVersion("0.12.0");

        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildPlanScript(stack, module)).thenReturn("");

        // when
        stackRunner.plan(job, module, stack);

        // then
        assertEquals("0.12.0", job.getCliVersion());
        verify(builder).image("hashicorp/terraform:0.12.0");
        verify(dockerClient).pull("hashicorp/terraform:0.12.0");
    }

    @Test
    void apply_shouldConsiderModuleCLIVersion() throws Exception {
        // given
        module.setCliVersion("0.12.0");

        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // when
        stackRunner.apply(job, module, stack);

        // then
        assertEquals("0.12.0", job.getCliVersion());
        verify(builder).image("hashicorp/terraform:0.12.0");
        verify(dockerClient).pull("hashicorp/terraform:0.12.0");
    }

    @Test
    void stop_shouldConsiderModuleCLIVersion() throws Exception {
        // given
        module.setCliVersion("0.12.0");

        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildDestroyScript(stack, module)).thenReturn("");

        // when
        stackRunner.stop(job, module, stack);

        // then
        assertEquals("0.12.0", job.getCliVersion());
        verify(builder).image("hashicorp/terraform:0.12.0");
        verify(dockerClient).pull("hashicorp/terraform:0.12.0");
    }

    @Test
    void jobShouldBeRunned_with_TF_IN_AUTOMATION_envVar() throws Exception {
        // given
        httpHijackWorkaroundMock();
        containerExitMock(0L);
        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        when(settings.env()).thenReturn(List.of("SOME_VAR=SOME_VALUE"));

        // when
        stackRunner.apply(job, module, stack);

        // then
        verify(builder).env(List.of("TF_IN_AUTOMATION=true", "SOME_VAR=SOME_VALUE"));
    }

}