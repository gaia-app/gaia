package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository for jobs
 */
@RepositoryRestResource
public interface JobRepository extends MongoRepository<Job, String>{

    List<Job> findAllByStackId(String stackId);

}
