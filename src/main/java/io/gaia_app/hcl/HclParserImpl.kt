package io.gaia_app.hcl

import io.gaia_app.hcl.antlr.hclLexer
import io.gaia_app.hcl.antlr.hclParser
import io.gaia_app.modules.bo.Output
import io.gaia_app.modules.bo.Variable
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.springframework.stereotype.Service

interface HclParser {
    fun parseContent(content: String): HclVisitor
    fun parseVariables(content: String): List<Variable>
    fun parseOutputs(content: String): List<Output>
    fun parseProvider(content: String): String
}

@Service
class HclParserImpl : HclParser {

    override fun parseContent(content: String): HclVisitor {
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

    override fun parseVariables(content:String): List<Variable> {
        val hclVisitor = parseContent(content)
        return hclVisitor.variables
    }

    override fun parseOutputs(content:String): List<Output> {
        val hclVisitor = parseContent(content)
        return hclVisitor.outputs
    }

    override fun parseProvider(content: String): String {
        val hclVisitor = parseContent(content)
        return hclVisitor.provider
    }
}
