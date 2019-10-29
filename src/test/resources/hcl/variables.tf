// a full variable
variable "string_var" {
    type = "string"
    description = "a test string var"
    default = "foo"
}

# another one
variable "number_var" {
    description = "a test number var"
    type = number
    default = 42
}

/*
 * A multi line comment
 */
variable "bool_var" {
    default = false
}