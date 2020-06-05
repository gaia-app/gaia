package io.gaia_app.stacks.repository

import io.gaia_app.stacks.bo.Job
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Repository for jobs
 */
@Repository
interface JobRepository : MongoRepository<Job, String> {

    fun findAllByStackIdOrderByStartDateTimeDesc(stackId: String): List<Job>

}
