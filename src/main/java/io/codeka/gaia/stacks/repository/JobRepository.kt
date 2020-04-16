package io.codeka.gaia.stacks.repository

import io.codeka.gaia.stacks.bo.Job
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Repository for jobs
 */
@Repository
interface JobRepository : MongoRepository<Job, String> {

    fun findAllByStackIdOrderByStartDateTimeDesc(stackId: String): List<Job>

}
