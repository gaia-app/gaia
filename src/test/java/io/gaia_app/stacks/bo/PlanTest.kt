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

}
