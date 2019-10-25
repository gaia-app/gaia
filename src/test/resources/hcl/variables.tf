variable "string_var" {
    type = "string"
    description = "a test string var"
    default = "foo"
}

variable "number_var" {
    type = number
    description = "a test number var"
    default = 42
}

variable "bool_var" {
    default = false
}