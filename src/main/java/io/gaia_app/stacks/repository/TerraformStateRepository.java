package io.gaia_app.stacks.repository;

import io.gaia_app.stacks.bo.TerraformState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerraformStateRepository extends MongoRepository<TerraformState, String> {
}
