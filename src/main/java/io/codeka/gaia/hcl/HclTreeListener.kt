package io.codeka.gaia.hcl

import io.codeka.gaia.hcl.antlr.hclBaseListener
import io.codeka.gaia.hcl.antlr.hclParser
import java.util.*

class HclTreeListener : hclBaseListener() {

    var variables: MutableList<Variable> = LinkedList()

    private var current: Variable = Variable()

    override fun enterVariableDirective(ctx: hclParser.VariableDirectiveContext) {
        this.current = Variable()
        this.current.name = ctx.identifier().text
    }

    override fun exitVariableDirective(ctx: hclParser.VariableDirectiveContext) {
        this.variables.add(this.current)
    }

    override fun enterType(ctx: hclParser.TypeContext) {
        this.current.type = ctx.STRING().text
    }

    override fun enterDescription(ctx: hclParser.DescriptionContext) {
        this.current.description = ctx.STRING().text
    }

    override fun enterR_default(ctx: hclParser.R_defaultContext) {
        this.current.default = ctx.STRING().text
    }
}