variable "string_var" {
    type = "string"
    description = "a test string var"
    default = "foo"
}

variable "number_var" {
    description = "a test number var"
    type = number
    default = 42
}

variable "bool_var" {
    default = false
}