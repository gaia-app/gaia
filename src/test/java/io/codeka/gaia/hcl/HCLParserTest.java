package io.codeka.gaia.hcl;

import io.codeka.gaia.hcl.antlr.hclLexer;
import io.codeka.gaia.hcl.antlr.hclParser;
import io.codeka.gaia.modules.bo.Variable;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HCLParserTest {

    @Test
    void parsing_variables_shouldWork() throws IOException {
        // given
        // loading test file
        CharStream charStream = CharStreams.fromStream(new ClassPathResource("hcl/variables.tf").getInputStream());

        // configuring antlr lexer
        hclLexer lexer = new hclLexer(charStream);

        // configuring antlr parser
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        hclParser parser = new hclParser(tokenStream);

        // when
        // walk the AST
        ParseTreeWalker walker = new ParseTreeWalker();
        var hclTreeListener = new HclTreeListener();
        walker.walk(hclTreeListener, parser.file());

        // then
        assertThat(hclTreeListener.getVariables()).hasSize(3);

        var stringVar = new Variable("\"string_var\"");
        stringVar.setType("\"string\"");
        stringVar.setDescription("\"a test string var\"");
        stringVar.setDefaultValue("\"foo\"");

        var numberVar = new Variable("\"number_var\"");
        numberVar.setType("number");
        numberVar.setDescription("\"a test number var\"");
        numberVar.setDefaultValue("42");

        var boolVar = new Variable("\"bool_var\"");
        boolVar.setDefaultValue("false");

        assertThat(hclTreeListener.getVariables()).contains(stringVar, numberVar, boolVar);
    }

    @Test
    void parsing_outputs_shouldWork() throws IOException {
        // given
        // loading test file
        CharStream charStream = CharStreams.fromStream(new ClassPathResource("hcl/outputs.tf").getInputStream());

        // configuring antlr lexer
        hclLexer lexer = new hclLexer(charStream);

        // configuring antlr parser
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        hclParser parser = new hclParser(tokenStream);

        // when
        // walk the AST
        ParseTreeWalker walker = new ParseTreeWalker();
        var hclTreeListener = new HclTreeListener();
        walker.walk(hclTreeListener, parser.file());

        // then
        assertThat(hclTreeListener.getOutputs()).hasSize(2);

        var output1 = new Output("\"instance_ip_addr\"", "\"${aws_instance.server.private_ip}\"", "\"The private IP address of the main server instance.\"", "false");
        var output2 = new Output("\"db_password\"", "aws_db_instance.db[1].password", "\"The password for logging in to the database.\"", "true");

        assertThat(hclTreeListener.getOutputs()).contains(output1, output2);
    }

    @Test
    void parsing_variables_shouldWorkWithVisitor() throws IOException {
        // given
        // loading test file
        CharStream charStream = CharStreams.fromStream(new ClassPathResource("hcl/variables.tf").getInputStream());

        // configuring antlr lexer
        hclLexer lexer = new hclLexer(charStream);

        // configuring antlr parser
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        hclParser parser = new hclParser(tokenStream);

        // when
        // visit the AST
        var hclVisitor = new HclVisitor();
        hclVisitor.visit(parser.file());

        // then
        assertThat(hclVisitor.getVariables()).hasSize(3);

        var stringVar = new Variable("\"string_var\"");
        stringVar.setType("\"string\"");
        stringVar.setDescription("\"a test string var\"");
        stringVar.setDefaultValue("\"foo\"");

        var numberVar = new Variable("\"number_var\"");
        numberVar.setType("number");
        numberVar.setDescription("\"a test number var\"");
        numberVar.setDefaultValue("42");

        var boolVar = new Variable("\"bool_var\"");
        boolVar.setDefaultValue("false");

        assertThat(hclVisitor.getVariables()).contains(stringVar, numberVar, boolVar);
    }

    @Test
    void parsing_outputs_shouldWorkWithVisitor() throws IOException {
        // given
        // loading test file
        CharStream charStream = CharStreams.fromStream(new ClassPathResource("hcl/outputs.tf").getInputStream());

        // configuring antlr lexer
        hclLexer lexer = new hclLexer(charStream);

        // configuring antlr parser
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        hclParser parser = new hclParser(tokenStream);

        // when
        // walk the AST
        var hclVisitor = new HclVisitor();
        hclVisitor.visit(parser.file());

        // then
        assertThat(hclVisitor.getOutputs()).hasSize(2);

        var output1 = new Output("\"instance_ip_addr\"", "\"${aws_instance.server.private_ip}\"", "\"The private IP address of the main server instance.\"", "false");
        var output2 = new Output("\"db_password\"", "aws_db_instance.db[1].password", "\"The password for logging in to the database.\"", "true");

        assertThat(hclVisitor.getOutputs()).contains(output1, output2);
    }

}