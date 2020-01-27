package io.codeka.gaia.hcl

import io.codeka.gaia.modules.bo.Output
import io.codeka.gaia.modules.bo.Variable
import org.apache.commons.io.IOUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.nio.charset.Charset

class HCLParserTest {

    private val hclParser = HclParserImpl()

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWorkWithVisitor() {
        // given
        val fileContent = IOUtils.toString(ClassPathResource("hcl/variables.tf").url, Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        Assertions.assertThat(variables).hasSize(3)
        val stringVar = Variable("string_var", "string", "a test string var", "foo")
        val numberVar = Variable("number_var", "number", "a test number var", "42")
        val boolVar = Variable("bool_var", defaultValue = "false")
        Assertions.assertThat(variables).contains(stringVar, numberVar, boolVar)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWork_withComplexFile() {
        // given
        val fileContent = IOUtils.toString(ClassPathResource("hcl/variables_aws_eks.tf").url, Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        Assertions.assertThat(variables).hasSize(49)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWork_withAnotherComplexFile() {
        // given
        val fileContent = IOUtils.toString(ClassPathResource("hcl/variables_aws_vpc.tf").url, Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        Assertions.assertThat(variables).hasSize(282)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_outputs_shouldWorkWithVisitor() {
        // given
        val fileContent = IOUtils.toString(ClassPathResource("hcl/outputs.tf").url, Charset.defaultCharset())

        // when
        val outputs = hclParser.parseOutputs(fileContent)

        // then
        Assertions.assertThat(outputs).hasSize(2)
        val output1 = Output("instance_ip_addr", "\${aws_instance.server.private_ip}", "The private IP address of the main server instance.", "false")
        val output2 = Output("db_password", "aws_db_instance.db[1].password", "The password for logging in to the database.", "true")
        Assertions.assertThat(outputs).contains(output1, output2)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_outputs_shouldWork_withComplexFile() {
        // given
        val fileContent = IOUtils.toString(ClassPathResource("hcl/outputs_aws_eks.tf").url, Charset.defaultCharset())

        // when
        val outputs = hclParser.parseOutputs(fileContent)

        // then
        Assertions.assertThat(outputs).hasSize(27)
    }
}