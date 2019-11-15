package io.codeka.gaia.hcl

import io.codeka.gaia.hcl.antlr.hclLexer
import io.codeka.gaia.hcl.antlr.hclParser
import io.codeka.gaia.modules.bo.Output
import io.codeka.gaia.modules.bo.Variable
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class HclParser {

    private fun parseContent(content: String): HclVisitor {
        // loading test file
        val charStream = CharStreams.fromString(content)

        // configuring antlr lexer
        val lexer = hclLexer(charStream)

        // using the lexer to configure a token stream
        val tokenStream = CommonTokenStream(lexer)

        // configuring antlr parser using the token stream
        val parser = hclParser(tokenStream)

        // visit the AST
        val hclVisitor = HclVisitor()
        hclVisitor.visit(parser.file())
        return hclVisitor
    }

    fun parseVariables(content:String): List<Variable> {
        val hclVisitor = parseContent(content)
        return hclVisitor.variables
    }

    fun parseOutputs(content:String): List<Output> {
        val hclVisitor = parseContent(content)
        return hclVisitor.outputs
    }
}