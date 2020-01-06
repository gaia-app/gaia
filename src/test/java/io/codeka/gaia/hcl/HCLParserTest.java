package io.codeka.gaia.hcl;

import io.codeka.gaia.modules.bo.Output;
import io.codeka.gaia.modules.bo.Variable;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

class HCLParserTest {

    private HclParserImpl hclParser = new HclParserImpl();

    @Test
    void parsing_variables_shouldWorkWithVisitor() throws IOException {
        // given
        var fileContent = IOUtils.toString(new ClassPathResource("hcl/variables.tf").getURL(), Charset.defaultCharset());

        // when
        var variables = hclParser.parseVariables(fileContent);

        // then
        assertThat(variables).hasSize(3);

        var stringVar = new Variable("string_var");
        stringVar.setType("string");
        stringVar.setDescription("a test string var");
        stringVar.setDefaultValue("foo");

        var numberVar = new Variable("number_var");
        numberVar.setType("number");
        numberVar.setDescription("a test number var");
        numberVar.setDefaultValue("42");

        var boolVar = new Variable("bool_var");
        boolVar.setDefaultValue("false");

        assertThat(variables).contains(stringVar, numberVar, boolVar);
    }

    @Test
    void parsing_variables_shouldWork_withComplexFile() throws IOException {
        // given
        var fileContent = IOUtils.toString(new ClassPathResource("hcl/variables_aws_eks.tf").getURL(), Charset.defaultCharset());

        // when
        var variables = hclParser.parseVariables(fileContent);

        // then
        assertThat(variables).hasSize(49);
    }

    @Test
    void parsing_variables_shouldWork_withAnotherComplexFile() throws IOException {
        // given
        var fileContent = IOUtils.toString(new ClassPathResource("hcl/variables_aws_vpc.tf").getURL(), Charset.defaultCharset());

        // when
        var variables = hclParser.parseVariables(fileContent);

        // then
        assertThat(variables).hasSize(282);
    }

    @Test
    void parsing_outputs_shouldWorkWithVisitor() throws IOException {
        // given
        var fileContent = IOUtils.toString(new ClassPathResource("hcl/outputs.tf").getURL(), Charset.defaultCharset());

        // when
        var outputs = hclParser.parseOutputs(fileContent);

        // then
        assertThat(outputs).hasSize(2);

        var output1 = new Output("instance_ip_addr", "${aws_instance.server.private_ip}", "The private IP address of the main server instance.", "false");
        var output2 = new Output("db_password", "aws_db_instance.db[1].password", "The password for logging in to the database.", "true");

        assertThat(outputs).contains(output1, output2);
    }

    @Test
    void parsing_outputs_shouldWork_withComplexFile() throws IOException {
        // given
        var fileContent = IOUtils.toString(new ClassPathResource("hcl/outputs_aws_eks.tf").getURL(), Charset.defaultCharset());

        // when
        var outputs = hclParser.parseOutputs(fileContent);

        // then
        assertThat(outputs).hasSize(27);
    }

}