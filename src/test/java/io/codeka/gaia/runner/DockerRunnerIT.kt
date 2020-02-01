package io.codeka.gaia.runner

import io.codeka.gaia.modules.bo.TerraformImage
import io.codeka.gaia.runner.config.DockerConfig
import io.codeka.gaia.settings.bo.Settings
import io.codeka.gaia.stacks.bo.Job
import io.codeka.gaia.stacks.workflow.JobWorkflow
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [DockerRunner::class, DockerConfig::class, Settings::class, HttpHijackWorkaround::class])
@EnableConfigurationProperties
@TestPropertySource(properties = ["gaia.dockerDaemonUrl=unix:///var/run/docker.sock"])
class DockerRunnerIT {

    @Autowired
    private lateinit var dockerRunner: DockerRunner

    @Test
    fun `runContainerForJob() should work with a simple script`() {
        val script = "echo 'Hello World'"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)

        Assert.assertEquals(0, dockerRunner.runContainerForJob(jobWorkflow, script).toLong())
    }

    @Test
    fun `runContainerForJob() should return the script exit code`() {
        val script = "exit 5"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)

        Assert.assertEquals(5, dockerRunner.runContainerForJob(jobWorkflow, script).toLong())
    }
}