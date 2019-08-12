package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.stacks.bo.Job;
import io.codeka.gaia.stacks.bo.JobStatus;
import io.codeka.gaia.stacks.bo.JobType;
import io.codeka.gaia.teams.bo.User;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobTest {

    @Test
    void getLogs_shouldReturnOutputStreamResult() {
        var job = new Job(new User());

        PrintWriter printWriter = new PrintWriter(job.getLogsWriter());
        printWriter.println("Test Line 1");
        printWriter.println("Test Line 2");

        var logs = job.getLogs();

        assertEquals("Test Line 1\nTest Line 2\n", logs);
    }

    @Test
    void start_shouldSetStatusToRunning() {
        var job = new Job(new User());

        job.start(null);

        assertEquals(JobStatus.RUNNING, job.getStatus());
    }

    @Test
    void start_shouldSetType() {
        var job = new Job(new User());

        job.start(JobType.RUN);

        assertEquals(JobType.RUN, job.getType());
    }

    @Test
    void start_shouldSetStartDateTime() {
        var job = new Job(new User());
        job.setStartDateTime(null);

        job.start(null);

        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldSetStatusToFinished() {
        var job = new Job(new User());
        job.setStartDateTime(LocalDateTime.now());

        job.end();

        assertEquals(JobStatus.FINISHED, job.getStatus());
    }

    @Test
    void end_shouldSetEndDateTime() {
        var job = new Job(new User());
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(null);
        job.setExecutionTime(null);

        job.end();
        var timer = Duration.between(job.getStartDateTime(), job.getEndDateTime()).toMillis();

        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
        assertThat(job.getExecutionTime()).isNotNull().isEqualTo(timer);
    }

    @Test
    void fail_shouldSetStatusToFinished() {
        var job = new Job(new User());
        job.setStartDateTime(LocalDateTime.now());

        job.fail();

        assertEquals(JobStatus.FAILED, job.getStatus());
    }

    @Test
    void fail_shouldSetEndDateTime() {
        var job = new Job(new User());
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(null);
        job.setExecutionTime(null);

        job.fail();
        var timer = Duration.between(job.getStartDateTime(), job.getEndDateTime()).toMillis();

        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
        assertThat(job.getExecutionTime()).isNotNull().isEqualTo(timer);
    }

    @Test
    void job_shouldSetUser() {
        var user = new User("test");

        var job = new Job(user);

        assertThat(job.getUser()).isNotNull().isEqualTo(user);
    }

    @Test
    void job_shouldThrowAnExceptionIfUserIsNull() {
        assertThrows(AssertionError.class, () -> new Job(null));
    }

}