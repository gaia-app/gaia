package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.teams.bo.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A job instantiates or stops a stack
 */
public class Job {

    private String id;
    private String stackId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private JobType type;
    private JobStatus status;
    private String cliVersion;
    @DBRef
    private List<Step> steps = new ArrayList<>(2);
    @DBRef
    private User user;

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

    public void reset() {
        this.status = null;
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

    public String getCliVersion() {
        return cliVersion;
    }

    public void setCliVersion(String cliVersion) {
        this.cliVersion = cliVersion;
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
        if (CollectionUtils.isEmpty(this.steps)) {
            return null;
        }
        return this.steps.stream()
                .map(Step::getExecutionTime)
                .filter(Objects::nonNull)
                .reduce(null, (a, b) -> a == null ? b : a + b);
    }
}
