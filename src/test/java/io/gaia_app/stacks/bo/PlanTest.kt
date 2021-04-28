package io.gaia_app.stacks.bo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

internal class PlanTest {

    @Test
    fun `terraform plan in json format should be parsed in plan object`(){
        val objectMapper = jacksonObjectMapper()

        val jsonPlan = ClassPathResource("/sample-plan.json")

        val plan = objectMapper.readValue(jsonPlan.file, Plan::class.java)

        assertThat(plan.terraform_version).isEqualTo("0.12.28")
        assertThat(plan.resource_changes).hasSize(4)

        val container = ResourceChange("docker_container.mongo", "docker", "docker_container", Change(listOf(ChangesTypes.CREATE, ChangesTypes.DELETE)))
        val image = ResourceChange("docker_image.mongo", "docker", "docker_image", Change(listOf(ChangesTypes.CREATE)))

        assertThat(plan.resource_changes).containsAll(listOf(container, image))
    }

    @Test
    fun `a plan with no changes should be 'up to date'`() {
        val plan = Plan(terraform_version = "0", resource_changes = listOf())
        assertThat(plan.isUpToDate()).isTrue
    }

    @Test
    fun `a plan with only no-op changes should be 'up to date'`() {
        val noOpChange = ResourceChange("address", "provider", "type", Change(listOf(ChangesTypes.NOOP)))
        val plan = Plan(terraform_version = "0", resource_changes = listOf(noOpChange))
        assertThat(plan.isUpToDate()).isTrue
    }

    @Test
    fun `a plan with at least one create,update, or delete should not be 'up to date'`() {
        val createChange = ResourceChange("address", "provider", "type", Change(listOf(ChangesTypes.CREATE)))
        val updateChange = ResourceChange("address", "provider", "type", Change(listOf(ChangesTypes.UPDATE)))
        val deleteChange = ResourceChange("address", "provider", "type", Change(listOf(ChangesTypes.DELETE)))

        assertThat(Plan(terraform_version = "0", resource_changes = listOf(createChange)).isUpToDate()).isFalse
        assertThat(Plan(terraform_version = "0", resource_changes = listOf(updateChange)).isUpToDate()).isFalse
        assertThat(Plan(terraform_version = "0", resource_changes = listOf(deleteChange)).isUpToDate()).isFalse
    }

}
