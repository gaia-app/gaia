package io.codeka.gaia.runner;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DockerContainerTest {

    @InjectMocks
    private DockerContainer dockerContainer;

    @Mock
    private HttpHijackWorkaround httpHijackWorkaround;

    @Mock
    private OutputStream out;

    @Mock
    private DockerClient dockerClient;

    @Mock
    private LogStream logStream;

    @BeforeEach
    void setUp() throws DockerException, InterruptedException {
        when(dockerClient.attachContainer(any(), any())).thenReturn(logStream);
    }

    @Test
    void getStdIn_shouldUseHijacking() throws IOException, StackRunnerException, DockerException, InterruptedException {
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(out);

        var stdIn = dockerContainer.getStdIn();
        assertEquals(out, stdIn);

        verify(dockerClient).attachContainer(any(), any());
    }

    @Test
    void getStdIn_shouldThrowAnException_whenUnableToAttachToTheContainer() throws DockerException, InterruptedException {
        when(dockerClient.attachContainer(any(), any())).thenThrow(new DockerException("test"));

        var exception = assertThrows(StackRunnerException.class, () -> dockerContainer.getStdIn());
        assertEquals("unable to attach to the container", exception.getMessage());
    }

    @Test
    void getStdIn_shouldThrowAnException_whenAttachThreadIsInterrupted() throws DockerException, InterruptedException {
        when(dockerClient.attachContainer(any(), any())).thenThrow(new InterruptedException("test"));

        var exception = assertThrows(StackRunnerException.class, () -> dockerContainer.getStdIn());
        assertEquals("unable to attach to the container, the thread was interrupted", exception.getMessage());
    }

    @Test
    void getStdIn_shouldThrowAnException_whenHttpHijackingFails() throws IOException {
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenThrow(new IOException("test"));

        var exception = assertThrows(StackRunnerException.class, () -> dockerContainer.getStdIn());
        assertEquals("unable to get container std in", exception.getMessage());
    }

    @Test
    void attach_shouldAttachToTheLogStream() throws StackRunnerException, IOException, DockerException, InterruptedException {
        var stdOut = mock(OutputStream.class);
        var stdErr = mock(OutputStream.class);
        dockerContainer.attach(stdOut, stdErr);

        verify(dockerClient).attachContainer(any(), any());
        verify(logStream).attach(stdOut, stdErr, true);
    }

    @Test
    void attach_shouldThrowAnException_whenLogStreamAttachingFails() throws IOException {
        doThrow(new IOException("test")).when(logStream)
                .attach(any(), any(), anyBoolean());

        var stdOut = mock(OutputStream.class);
        var stdErr = mock(OutputStream.class);

        var exception = assertThrows(StackRunnerException.class, () -> dockerContainer.attach(stdOut, stdErr));
        assertEquals("unable to attach to container std out and std err", exception.getMessage());
    }

    @Test
    void dockerAttach_shouldOnlyBeDoneOnce() throws DockerException, InterruptedException, StackRunnerException, IOException {
        when(httpHijackWorkaround.getOutputStream(any(), any())).thenReturn(out);

        dockerContainer.getStdIn();

        var stdOut = mock(OutputStream.class);
        var stdErr = mock(OutputStream.class);
        dockerContainer.attach(stdOut, stdErr);

        verify(dockerClient).attachContainer(any(), any());
        verifyNoMoreInteractions(dockerClient);
    }

}