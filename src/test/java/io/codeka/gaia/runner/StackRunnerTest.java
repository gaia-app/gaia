package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerExit;
import io.codeka.gaia.bo.*;
import io.codeka.gaia.repository.JobRepository;
import io.codeka.gaia.repository.StackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.channels.WritableByteChannel;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void job_shouldBeSavedToDatabaseAfterRun() throws Exception {
        var job = new Job();
        var module = new TerraformModule();
        var stack = new Stack();

        var stackRunner = new StackRunner(dockerClient, builder, settings, stackCommandBuilder, stackRepository, httpHijackWorkaround, jobRepository);

        // simulating a container with id 12
        var containerCreation = mock(ContainerCreation.class);
        when(containerCreation.id()).thenReturn("12");
        when(dockerClient.createContainer(any())).thenReturn(containerCreation);

        // setting mocks to let test pass till the end
        var writableByteChannel = mock(WritableByteChannel.class);
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(writableByteChannel);

        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // given
        var containerExit = mock(ContainerExit.class);
        when(containerExit.statusCode()).thenReturn(0L);
        when(dockerClient.waitContainer("12")).thenReturn(containerExit);

        // when
        stackRunner.apply(job, module, stack);

        // then
        verify(jobRepository).save(job);
    }

    @Test
    void successfullJob_shouldSetTheStackStateToRunning() throws Exception {
        var job = new Job();
        var module = new TerraformModule();
        var stack = new Stack();

        var stackRunner = new StackRunner(dockerClient, builder, settings, stackCommandBuilder, stackRepository, httpHijackWorkaround, jobRepository);

        // simulating a container with id 12
        var containerCreation = mock(ContainerCreation.class);
        when(containerCreation.id()).thenReturn("12");
        when(dockerClient.createContainer(any())).thenReturn(containerCreation);

        // setting mocks to let test pass till the end
        var writableByteChannel = mock(WritableByteChannel.class);
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(writableByteChannel);

        when(stackCommandBuilder.buildApplyScript(stack, module)).thenReturn("");

        // given
        var containerExit = mock(ContainerExit.class);
        when(containerExit.statusCode()).thenReturn(0L);
        when(dockerClient.waitContainer("12")).thenReturn(containerExit);

        // when
        stackRunner.apply(job, module, stack);

        // then
        assertEquals(StackState.RUNNING, stack.getState());
        verify(stackRepository).save(stack);
    }

}