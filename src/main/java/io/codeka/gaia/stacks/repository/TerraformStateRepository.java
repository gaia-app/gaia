package io.codeka.gaia.stacks.repository;

import io.codeka.gaia.stacks.bo.TerraformState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerraformStateRepository extends MongoRepository<TerraformState, String> {
}