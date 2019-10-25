package io.codeka.gaia.hcl

import io.codeka.gaia.hcl.antlr.hclBaseListener
import io.codeka.gaia.hcl.antlr.hclParser
import java.util.*

class HclTreeListener : hclBaseListener() {

    var variables: MutableList<Variable> = LinkedList()
    var outputs: MutableList<Output> = LinkedList()

    private var currentVariable: Variable = Variable()
    private var currentOutput:Output = Output()

    override fun enterVariableDirective(ctx: hclParser.VariableDirectiveContext) {
        this.currentVariable = Variable()
        this.currentVariable.name = ctx.identifier().text
    }

    override fun exitVariableDirective(ctx: hclParser.VariableDirectiveContext) {
        this.variables.add(this.currentVariable)
    }

    override fun enterType(ctx: hclParser.TypeContext) {
        this.currentVariable.type = ctx.TYPE().text
    }

    override fun enterVariableDescription(ctx: hclParser.VariableDescriptionContext) {
        this.currentVariable.description = ctx.STRING().text
    }

    override fun enterR_default(ctx: hclParser.R_defaultContext) {
        this.currentVariable.default = ctx.expression().text
    }

    override fun enterOutputDirective(ctx: hclParser.OutputDirectiveContext) {
        this.currentOutput = Output(name = ctx.identifier().text)
    }

    override fun exitOutputDirective(ctx: hclParser.OutputDirectiveContext) {
        this.outputs.add(this.currentOutput)
    }

    override fun enterOutputValue(ctx: hclParser.OutputValueContext) {
        this.currentOutput.value = ctx.expression().text
    }

    override fun enterOutputDescription(ctx: hclParser.OutputDescriptionContext) {
        this.currentOutput.description = ctx.STRING().text
    }

    override fun enterSensitive(ctx: hclParser.SensitiveContext) {
        this.currentOutput.sensitive = ctx.BOOLEAN().text
    }

}