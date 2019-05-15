package io.codeka.gaia.repository;

import io.codeka.gaia.bo.TerraformModule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TerraformModuleRepository extends MongoRepository<TerraformModule, String> {
}
