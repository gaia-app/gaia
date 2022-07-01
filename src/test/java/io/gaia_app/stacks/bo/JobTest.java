package io.gaia_app.stacks.bo;

import io.gaia_app.organizations.User;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JobTest {

    @Test
    void start_shouldSetStatusToPlanStarted() {
        // given
        var job = new Job();

        // when
        job.start();

        // then
        assertEquals(JobStatus.PLAN_STARTED, job.getStatus());
    }

    @Test
    void start_shouldSetStartDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(null);

        // when
        job.start();

        // then
        assertThat(job.getStartDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void end_shouldSetStatus() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        // when
        job.end(JobStatus.PLAN_FINISHED);

        // then
        assertEquals(JobStatus.PLAN_FINISHED, job.getStatus());
    }

    @Test
    void end_shouldSetEndDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(null);

        // when
        job.end(JobStatus.PLAN_FINISHED);

        // then
        assertThat(job.getEndDateTime()).isNotNull().isEqualToIgnoringSeconds(LocalDateTime.now());
    }

    @Test
    void reset_shouldResetStatusToPlanPending() {
        // given
        var job = new Job();
        job.setStatus(JobStatus.PLAN_STARTED);

        // when
        job.reset();

        // then
        assertThat(job.getStatus()).isEqualTo(JobStatus.PLAN_PENDING);
    }

    @Test
    void reset_shouldResetStartDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        // when
        job.reset();

        // then
        assertNull(job.getStartDateTime());
    }

    @Test
    void reset_shouldResetEndDateTime() {
        // given
        var job = new Job();
        job.setEndDateTime(LocalDateTime.now());

        // when
        job.reset();

        // then
        assertNull(job.getEndDateTime());
    }

    @Test
    void reset_shouldResetSteps() {
        // given
        var job = new Job();
        job.getSteps().add(new Step());
        job.getSteps().add(new Step());

        // when
        job.reset();

        // then
        assertThat(job.getSteps()).isNotNull().isEmpty();
    }

    @Test
    void proceed_shouldSetStatus() {
        // given
        var job = new Job();

        // when
        job.proceed(JobStatus.APPLY_STARTED);

        // then
        assertEquals(JobStatus.APPLY_STARTED, job.getStatus());
    }

    @Test
    void proceed_shouldResetEndDateTime() {
        // given
        var job = new Job();
        job.setEndDateTime(LocalDateTime.now());

        // when
        job.proceed(null);

        // then
        assertNull(job.getEndDateTime());
    }

    @Test
    void job_shouldSetId() {
        var job = new Job(null, null, null);

        assertThat(job.getId()).isNotBlank();
    }

    @Test
    void job_shouldSetType() {
        // when
        var job = new Job(JobType.RUN, null, null);

        // then
        assertThat(job.getType()).isNotNull().isEqualTo(JobType.RUN);
    }

    @Test
    void job_shouldSetStackId() {
        // when
        var job = new Job(null, "stackId_test", null);

        // then
        assertThat(job.getStackId()).isNotNull().isEqualTo("stackId_test");
    }

    @Test
    void job_shouldSetUser() {
        // given
        var user = new User("test", null);

        // when
        var job = new Job(null, null, user);

        // then
        assertThat(job.getUser()).isNotNull().isEqualTo(user);
    }

    @Test
    void getExecutionTime_shouldReturnDuration_betweenStartDateTime_andEndDateTime() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());
        job.setEndDateTime(LocalDateTime.now());
        var timer = Duration.between(job.getStartDateTime(), job.getEndDateTime()).toMillis();

        // when
        var result = job.getExecutionTime();

        // then
        assertThat(result).isNotNull().isEqualTo(timer);
    }

    @Test
    void getExecutionTime_shouldReturnNull_whenStartDateTimeIsNull() {
        // given
        var job = new Job();
        job.setEndDateTime(LocalDateTime.now());

        // when
        var result = job.getExecutionTime();

        // then
        assertNull(result);
    }

    @Test
    void getExecutionTime_shouldReturnNull_whenEndDateTimeIsNull() {
        // given
        var job = new Job();
        job.setStartDateTime(LocalDateTime.now());

        // when
        var result = job.getExecutionTime();

        // then
        assertNull(result);
    }
}
