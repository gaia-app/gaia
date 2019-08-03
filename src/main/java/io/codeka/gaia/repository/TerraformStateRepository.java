package io.codeka.gaia.repository;

import io.codeka.gaia.bo.TerraformState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerraformStateRepository extends MongoRepository<TerraformState, String> {
}