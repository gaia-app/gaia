package io.gaia_app.hcl

import io.gaia_app.hcl.antlr.hclParser
import io.gaia_app.modules.bo.Output
import io.gaia_app.modules.bo.Variable
import java.util.*

class HclVisitor : io.gaia_app.hcl.antlr.hclBaseVisitor<Unit>() {

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
        val resourceProvider = ctx.STRING(0).text
            .removeSurrounding("\"")
            .substringBefore("_")

        if (! IGNORED_PROVIDERS.contains(resourceProvider)) {
            provider = resourceProvider
        }
    }

    override fun visitTerraformDirective(ctx: hclParser.TerraformDirectiveContext) {
        try {
            // find required providers field
            val requiredProviders = ctx.`object`().field()
                .first { it.IDENTIFIER().text == "required_providers" }
            // find first required provider that should not be ignored
            val firstValidProvider = requiredProviders.`object`().field()
                .first { ! IGNORED_PROVIDERS.contains(it.IDENTIFIER().text) }
            provider = firstValidProvider.IDENTIFIER().text
        }
        catch (_: NoSuchElementException){
            // NoSuchElementException is raised when no provider is found in the terraform directive
        }
    }
}
