package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.Step;
import io.gaia_app.stacks.bo.StepStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for steps
 */
@Repository
public interface StepRepository extends MongoRepository<Step, String> {

    void deleteByJobId(String jobId);

    Optional<Step> findFirstByStatus(StepStatus status);
}
