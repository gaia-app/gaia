package io.gaia_app.hcl

import io.gaia_app.modules.bo.Output
import io.gaia_app.modules.bo.Variable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

class HCLParserTest {

    private val hclParser = HclParserImpl()

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWorkWithVisitor() {
        // given
        val fileContent = Files.readString(Path.of(ClassPathResource("hcl/variables.tf").uri), Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        assertThat(variables).hasSize(3)
        val stringVar = Variable("string_var", "string", "a test string var", "foo")
        val numberVar = Variable("number_var", "number", "a test number var", "42")
        val boolVar = Variable("bool_var", defaultValue = "false")
        assertThat(variables).contains(stringVar, numberVar, boolVar)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWork_withComplexFile() {
        // given
        val fileContent = Files.readString(Path.of(ClassPathResource("hcl/variables_aws_eks.tf").uri), Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        assertThat(variables).hasSize(49)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_variables_shouldWork_withAnotherComplexFile() {
        // given
        val fileContent = Files.readString(Path.of(ClassPathResource("hcl/variables_aws_vpc.tf").uri), Charset.defaultCharset())

        // when
        val variables = hclParser.parseVariables(fileContent)

        // then
        assertThat(variables).hasSize(282)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_outputs_shouldWorkWithVisitor() {
        // given
        val fileContent = Files.readString(Path.of(ClassPathResource("hcl/outputs.tf").uri), Charset.defaultCharset())

        // when
        val outputs = hclParser.parseOutputs(fileContent)

        // then
        assertThat(outputs).hasSize(2)
        val output1 = Output("instance_ip_addr", "\${aws_instance.server.private_ip}", "The private IP address of the main server instance.", "false")
        val output2 = Output("db_password", "aws_db_instance.db[1].password", "The password for logging in to the database.", "true")
        assertThat(outputs).contains(output1, output2)
    }

    @Test
    @Throws(IOException::class)
    fun parsing_outputs_shouldWork_withComplexFile() {
        // given
        val fileContent = Files.readString(Path.of(ClassPathResource("hcl/outputs_aws_eks.tf").uri), Charset.defaultCharset())

        // when
        val outputs = hclParser.parseOutputs(fileContent)

        // then
        assertThat(outputs).hasSize(27)
    }

    /**
     * Tests for the provider part
     */
    @Nested
    inner class ProviderTest {

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldWork_withMainFile_includingProviderDirective() {
            // given
            val fileContent = Files.readString(Path.of(ClassPathResource("hcl/terraform_docker_mongo_main_with_provider.tf").uri), Charset.defaultCharset())
            // when
            val provider: String = hclParser.parseProvider(fileContent)
            // then
            assertThat(provider).isEqualTo("docker")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldWork_withMainFile_withoutProviderDirective() {
            // given
            val fileContent = Files.readString(Path.of(ClassPathResource("hcl/terraform_docker_mongo_main_without_provider.tf").uri), Charset.defaultCharset())

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("docker")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldReturn_unknown_ifNoProviderFound() {
            // given
            val fileContent = Files.readString(Path.of(ClassPathResource("hcl/variables.tf").uri), Charset.defaultCharset())

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("unknown")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_fromResources_shouldIgnoreDummyProviders() {
            // given
            val fileContent = """
                resource "random_id" "id" {
                }

                resource "google_project" "this" {
                }
            """.trimIndent()

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("google")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldAnalyseRequiredProvidersBlock() {
            // given
            val fileContent = """
                terraform {
                  required_version = ">= 0.14"
                  required_providers {
                    random = ">= 3.1.0"
                    google = ">= 3.64.0"
                  }
                }
            """.trimIndent()

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("google")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldAnalyseRequiredProvidersBlock_withoutRelevantProvider() {
            // given
            val fileContent = """
                terraform {
                  required_version = ">= 0.14"
                  required_providers {
                    random = ">= 3.1.0"
                  }
                }
            """.trimIndent()

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("unknown")
        }

        @Test
        @Throws(IOException::class)
        fun parsing_provider_shouldAnalyseRequiredProvidersBlock_withoutRequiredProviders() {
            // given
            val fileContent = """
                terraform {
                  required_version = ">= 0.14"
                }
            """.trimIndent()

            // when
            val provider: String = hclParser.parseProvider(fileContent)

            // then
            assertThat(provider).isEqualTo("unknown")
        }

        @Test
        fun dummyProvidersShouldBeIgnored() {
            assertThat(hclParser.parseProvider("""provider "null" {} """)).isEqualTo("unknown")
            assertThat(hclParser.parseProvider("""provider "template" {} """)).isEqualTo("unknown")
            assertThat(hclParser.parseProvider("""provider "random" {} """)).isEqualTo("unknown")
            assertThat(hclParser.parseProvider("""provider "terraform" {} """)).isEqualTo("unknown")
        }
    }
}
