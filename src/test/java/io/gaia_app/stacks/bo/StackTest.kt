package io.gaia_app.stacks.bo

import io.gaia_app.modules.bo.TerraformModule
import io.gaia_app.modules.bo.Variable
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class StackTest {

    @Test
    fun tfvars_shouldGenerateTfvarContents() {
        // given
        val variableWithDefault = Variable("variableWithDefault", "string", "a variable with a default value", "myValue")
        val mandatoryVariable = Variable("mandatoryVariable", "string", "a variable with a default value", null, true, true)

        val module = TerraformModule()
        module.variables = listOf(variableWithDefault, mandatoryVariable)

        val stack = Stack()
        stack.module = module
        stack.variableValues = mapOf("mandatoryVariable" to "myOtherValue")

        // when
        val tfvars = stack.tfvars()

        // then
        Assertions.assertThat(tfvars)
            .contains("variableWithDefault = \"myValue\"\n")
            .contains("mandatoryVariable = \"myOtherValue\"\n")
    }

    @Test
    fun tfvars_shouldGenerateTfvarContents_forComplexTypes() {
        // given
        val listOfNumbers = Variable("listOfNumbers", "list(number)", "a list variable")

        val module = TerraformModule()
        module.variables = listOf(listOfNumbers)

        val stack = Stack()
        stack.module = module
        stack.variableValues = mapOf("listOfNumbers" to "[1,2]")

        // then
        Assertions.assertThat(stack.tfvars())
            .contains("listOfNumbers = [1,2]\n")
    }

    @Test
    fun tfvars_shouldGenerateTfvarContents_forComplexTypesOnMultilineStrings() {
        // given
        val mapVariable = Variable("mapVariable", "map", "a map variable")

        val module = TerraformModule()
        module.variables = listOf(mapVariable)

        val stack = Stack()
        stack.module = module
        stack.variableValues = mapOf("mapVariable" to """
            {
                name = "Anakin"
                age  = 45
            }
        """.trimIndent())

        // then
        Assertions.assertThat(stack.tfvars())
            .contains("""
            mapVariable = {
                name = "Anakin"
                age  = 45
            }
            """.trimIndent())
    }
}
