package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Stack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository for stacks
 */
@RepositoryRestResource
public interface StackRepository extends MongoRepository<Stack, String>{
}
