package io.codeka.gaia.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

import java.io.StringWriter;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * A job is the instanciation of a stack
 */
public class Job {

    private String id;

    private String stackId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long executionTime;

    @Transient
    private StringWriter stringWriter = new StringWriter();

    private String logs;

    private JobStatus jobStatus;

    private JobType jobType;

    private String cliVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogs() {
        if(jobStatus == JobStatus.FINISHED || jobStatus == JobStatus.FAILED){
            return logs;
        }
        return stringWriter.toString();
    }

    @JsonIgnore
    public Writer getLogsWriter(){
        return stringWriter;
    }

    public JobStatus getStatus(){
        return this.jobStatus;
    }

    public void start(JobType jobType) {
        this.jobStatus = JobStatus.RUNNING;
        this.jobType = jobType;
        this.startDateTime = LocalDateTime.now();
    }

    public void end() {
        this.jobStatus = JobStatus.FINISHED;
        // getting final logs
        this.logs = this.stringWriter.toString();
        this.endDateTime = LocalDateTime.now();
        this.executionTime = Duration.between(startDateTime, endDateTime).toMillis();
    }

    public void fail() {
        this.jobStatus = JobStatus.FAILED;
        // getting final logs
        this.logs = this.stringWriter.toString();
        this.endDateTime = LocalDateTime.now();
        this.executionTime = Duration.between(startDateTime, endDateTime).toMillis();
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public JobType getType() {
        return this.jobType;
    }

    public String getCliVersion() {
        return cliVersion;
    }

    public void setCliVersion(String cliVersion) {
        this.cliVersion = cliVersion;
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
}
