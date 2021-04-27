package io.gaia_app.stacks.repository

import io.gaia_app.stacks.bo.Job
import io.gaia_app.stacks.bo.JobStatus
import org.jetbrains.annotations.NotNull
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for jobs
 */
@Repository
interface JobRepository : MongoRepository<Job, String> {

    fun findAllByStackIdOrderByScheduleTimeDesc(stackId: String): List<Job>

}
