package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for plan data
 */
@Repository
public interface PlanRepository extends MongoRepository<Plan, String> {}
