package io.codeka.gaia.hcl

import io.codeka.gaia.hcl.antlr.hclBaseVisitor
import io.codeka.gaia.hcl.antlr.hclParser
import io.codeka.gaia.modules.bo.Output
import io.codeka.gaia.modules.bo.Variable
import java.util.*

class HclVisitor : hclBaseVisitor<Unit>() {

    var variables: MutableList<Variable> = LinkedList()
    var outputs: MutableList<Output> = LinkedList()
    var provider: String = "unknown"

    private val IGNORED_PROVIDERS = setOf("null", "random", "template", "terraform")

    private var currentVariable: Variable = Variable(name = "")
    private var currentOutput: Output = Output()


    override fun visitVariableDirective(ctx: hclParser.VariableDirectiveContext) {
        currentVariable = Variable(name = ctx.STRING().text.removeSurrounding("\""))
        variables.add(currentVariable)
        visitChildren(ctx)
    }

    override fun visitVariableType(ctx: hclParser.VariableTypeContext) {
        currentVariable.type = ctx.type().text.removeSurrounding("\"")
    }

    override fun visitVariableDefault(ctx: hclParser.VariableDefaultContext) {
        currentVariable.defaultValue = ctx.expression().text.removeSurrounding("\"")
    }

    override fun visitVariableDescription(ctx: hclParser.VariableDescriptionContext) {
        currentVariable.description = ctx.STRING().text.removeSurrounding("\"")
    }

    override fun visitOutputDirective(ctx: hclParser.OutputDirectiveContext) {
        currentOutput = Output(name = ctx.STRING().text.removeSurrounding("\""))
        outputs.add(currentOutput)
        visitChildren(ctx)
    }

    override fun visitOutputDescription(ctx: hclParser.OutputDescriptionContext) {
        currentOutput.description = ctx.STRING().text.removeSurrounding("\"")
    }

    override fun visitOutputValue(ctx: hclParser.OutputValueContext) {
        currentOutput.value = ctx.expression().text.removeSurrounding("\"")
    }

    override fun visitOutputSensitive(ctx: hclParser.OutputSensitiveContext) {
        currentOutput.sensitive = ctx.BOOLEAN().text.removeSurrounding("\"")
    }

    override fun visitProviderDirective(ctx: hclParser.ProviderDirectiveContext) {
        val parsedProvider = ctx.STRING().text.removeSurrounding("\"")
        if (! IGNORED_PROVIDERS.contains(parsedProvider)) {
            provider = parsedProvider
        }
    }

    override fun visitResourceDirective(ctx: hclParser.ResourceDirectiveContext) {
        // provider already found !
        if (provider != "unknown") return

        // check first part of the resource type
        provider = ctx.STRING(0).text.removeSurrounding("\"")
                .substringBefore("_")
    }
}