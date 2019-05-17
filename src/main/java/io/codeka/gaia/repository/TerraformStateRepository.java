package io.codeka.gaia.repository;

import io.codeka.gaia.bo.TerraformState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TerraformStateRepository extends MongoRepository<TerraformState, String> {
}