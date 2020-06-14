package io.gaia_app.runner

import io.gaia_app.modules.bo.TerraformImage
import io.gaia_app.runner.config.DockerConfig
import io.gaia_app.settings.bo.Settings
import io.gaia_app.stacks.bo.Job
import io.gaia_app.stacks.bo.Step
import io.gaia_app.stacks.workflow.JobWorkflow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [DockerRunner::class, DockerConfig::class, Settings::class])
@EnableConfigurationProperties
@TestPropertySource(properties = ["gaia.dockerDaemonUrl=unix:///var/run/docker.sock"])
class DockerRunnerIT {

    @Autowired
    private lateinit var dockerRunner: DockerRunner

    @Test
    fun `runContainerForJob() should work with a simple script`() {
        val script = "echo 'Hello World'; exit 0;"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)
        jobWorkflow.currentStep = Step()

        assertEquals(0, dockerRunner.runContainerForJob(jobWorkflow, script).toLong())
    }

    @Test
    fun `runContainerForJob() should stop work with a simple script`() {
        val script = "set -e; echo 'Hello World'; false; exit 0;"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)
        jobWorkflow.currentStep = Step()

        assertEquals(1, dockerRunner.runContainerForJob(jobWorkflow, script).toLong())
    }

    @Test
    fun `runContainerForJob() should return the script exit code`() {
        val script = "exit 5"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)
        jobWorkflow.currentStep = Step()

        Assert.assertEquals(5, dockerRunner.runContainerForJob(jobWorkflow, script).toLong())
    }

    @Test
    fun `runContainerForJob() should feed step with container logs`() {
        val script = "echo 'hello world'; exit 0;"

        val job = Job()
        job.terraformImage = TerraformImage.defaultInstance()
        val jobWorkflow = JobWorkflow(job)
        jobWorkflow.currentStep = Step()

        dockerRunner.runContainerForJob(jobWorkflow, script)

        assertThat(jobWorkflow.currentStep.logs).isEqualTo("hello world\n");
    }
}
