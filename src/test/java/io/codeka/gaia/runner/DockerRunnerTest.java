package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import io.codeka.gaia.modules.bo.TerraformImage;
import io.codeka.gaia.settings.bo.Settings;
import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.stacks.workflow.JobWorkflow;
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
class DockerRunnerTest {

    @Mock
    private DockerClient dockerClient;

    @Mock(answer = Answers.RETURNS_SELF)
    private ContainerConfig.Builder builder;

    @Mock
    private Settings settings;

    @Mock
    private HttpHijackWorkaround httpHijackWorkaround;

    @Mock
    private JobWorkflow jobWorkflow;

    @InjectMocks
    private DockerRunner dockerRunner;

    @BeforeEach
    void setUp() throws Exception {
        // simulating a container with id 12
        var containerCreation = mock(ContainerCreation.class);
        when(containerCreation.id()).thenReturn("12");
        when(dockerClient.createContainer(any())).thenReturn(containerCreation);

        var writableByteChannel = mock(OutputStream.class);
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(writableByteChannel);

        var containerExit = mock(ContainerExit.class);
        lenient().when(containerExit.statusCode()).thenReturn(0L); // use lenient to prevent mockito from throwing exception for tests not needing this mock
        when(dockerClient.waitContainer(anyString())).thenReturn(containerExit);

        var job = new Job(JobType.RUN, null, null);
        job.setTerraformImage(new TerraformImage("hashicorp/terraform", "0.12.0"));
        when(jobWorkflow.getJob()).thenReturn(job);

        var containerConfig = mock(ContainerConfig.class);
        when(builder.build()).thenReturn(containerConfig);
        when(containerConfig.image()).thenReturn(job.getTerraformImage().image());
    }

    @Test
    void runContainerForJob_shouldUse_TF_IN_AUTOMATION_envVar() throws Exception {
        // when
        when(settings.env()).thenReturn(List.of("SOME_VAR=SOME_VALUE"));
        dockerRunner.runContainerForJob(jobWorkflow, "test_script");

        // then
        verify(builder).env(List.of("TF_IN_AUTOMATION=true", "SOME_VAR=SOME_VALUE"));
    }

    @Test
    void runContainerForJob_shouldConsiderJobModuleImageExecutor() throws Exception {
        // when
        dockerRunner.runContainerForJob(jobWorkflow, "test_script");

        // then
        verify(builder).image("hashicorp/terraform:0.12.0");
        verify(dockerClient).pull("hashicorp/terraform:0.12.0");
    }

    @Test
    void runContainerForJob_shouldReturnContainerStatus() throws Exception {
        // when
        var result = dockerRunner.runContainerForJob(jobWorkflow, "test_script");

        // then
        assertEquals(0, result);
    }

    @Test
    void runContainerForJob_shouldReturn99_whenInterruptedException() throws Exception {
        // when
        doThrow(InterruptedException.class).when(dockerClient).removeContainer(anyString());
        var result = dockerRunner.runContainerForJob(jobWorkflow, "test_script");

        // then
        assertEquals(99, result);
    }

    @Test
    void runContainerForJob_shouldReturn99_whenDockerException() throws Exception {
        // when
        doThrow(DockerException.class).when(dockerClient).removeContainer(anyString());
        var result = dockerRunner.runContainerForJob(jobWorkflow, "test_script");

        // then
        assertEquals(99, result);
    }
}