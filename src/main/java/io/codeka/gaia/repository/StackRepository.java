package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Stack;
import io.codeka.gaia.bo.StackState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for stacks
 */
@RepositoryRestResource
public interface StackRepository extends MongoRepository<Stack, String>{
    int countStacksByState(StackState state);
}
