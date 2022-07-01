package io.gaia_app.stacks.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gaia_app.credentials.Credentials;
import io.gaia_app.organizations.User;
import io.gaia_app.modules.bo.TerraformImage;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A job instantiates or stops a stack
 */
public class Job {

    private String id;
    private String stackId;
    private LocalDateTime scheduleTime = LocalDateTime.now();
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private JobType type;
    private JobStatus status;
    private TerraformImage terraformImage = TerraformImage.Companion.defaultInstance();
    @DBRef
    private List<Step> steps = new ArrayList<>(2);
    @DBRef
    private User user;

    @Transient
    @JsonIgnore
    private Credentials credentials;

    public Job() {
    }

    public Job(JobType jobType, String stackId, User user) {
        this.id = UUID.randomUUID().toString();
        this.type = jobType;
        this.stackId = stackId;
        this.user = user;
    }

    public void start() {
        this.status = JobStatus.PLAN_STARTED;
        this.startDateTime = LocalDateTime.now();
    }

    public void end(JobStatus jobStatus) {
        this.endDateTime = LocalDateTime.now();
        this.status = jobStatus;
    }

    public void proceed(JobStatus jobStatus) {
        this.endDateTime = null;
        this.status = jobStatus;
    }

    public void reset() {
        this.status = JobStatus.PLAN_PENDING;
        this.startDateTime = null;
        this.endDateTime = null;
        this.steps.clear();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
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

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public TerraformImage getTerraformImage() {
        return terraformImage;
    }

    public void setTerraformImage(TerraformImage terraformImage) {
        this.terraformImage = terraformImage;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getExecutionTime() {
        if (this.startDateTime == null || this.endDateTime == null) {
            return null;
        }
        return Duration.between(this.startDateTime, this.endDateTime).toMillis();
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
