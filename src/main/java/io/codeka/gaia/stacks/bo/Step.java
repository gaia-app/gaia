package io.codeka.gaia.stacks.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

import java.io.StringWriter;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A step is a part of a job. It can be a plan, an apply, etc.
 */
public class Step {

    private String id;
    private String jobId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long executionTime;
    private StepType type;
    private StepStatus status;
    @Transient
    private StringWriter logsWriter = new StringWriter();
    private String logs;

    public Step() {
    }

    public Step(StepType stepType, String jobId) {
        this.id = UUID.randomUUID().toString();
        this.type = stepType;
        this.jobId = jobId;
    }

    public void start() {
        this.startDateTime = LocalDateTime.now();
        this.status = StepStatus.STARTED;
    }

    public void end() {
        this.endDateTime = LocalDateTime.now();
        this.executionTime = Duration.between(this.startDateTime, this.endDateTime).toMillis();
        this.logs = this.logsWriter.toString();
        this.status = StepStatus.FINISHED;
    }

    public void fail() {
        this.endDateTime = LocalDateTime.now();
        this.executionTime = Duration.between(this.startDateTime, this.endDateTime).toMillis();
        this.logs = this.logsWriter.toString();
        this.status = StepStatus.FAILED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public String getLogs() {
        if (status == StepStatus.FINISHED || status == StepStatus.FAILED) {
            return logs;
        }
        return logsWriter.toString();
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    @JsonIgnore
    public Writer getLogsWriter() {
        return logsWriter;
    }

}
