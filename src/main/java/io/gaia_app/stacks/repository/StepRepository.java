package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.Step;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for steps
 */
@Repository
public interface StepRepository extends MongoRepository<Step, String> {

    void deleteByJobId(String jobId);

}
