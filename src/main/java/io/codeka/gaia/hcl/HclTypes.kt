package io.codeka.gaia.hcl

data class Variable(var name: String = "", var type: String = "", var description: String = "", var default: String = "");

data class Output(var name: String = "", var value: String = "", var description: String = "", var sensitive: String = "false");

