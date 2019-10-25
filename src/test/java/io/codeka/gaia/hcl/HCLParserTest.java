package io.codeka.gaia.hcl;

import io.codeka.gaia.hcl.antlr.hclLexer;
import io.codeka.gaia.hcl.antlr.hclParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HCLParserTest {

    @Test
    void parsing_variable_shouldWork() throws IOException {
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

        Variable stringVar = new Variable("\"string_var\"", "\"string\"", "\"a test string var\"", "\"foo\"");
        Variable numberVar = new Variable("\"number_var\"", "number", "\"a test number var\"", "42");
        Variable boolVar = new Variable("\"bool_var\"", "", "", "false");

        assertThat(hclTreeListener.getVariables()).contains(stringVar, numberVar, boolVar);
    }

}