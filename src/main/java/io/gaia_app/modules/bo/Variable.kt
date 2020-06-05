package io.gaia_app.modules.bo

import javax.validation.constraints.NotBlank

/**
 * Represents a module variable.
 * We use `@JvmOverloads` annotation to tell the Kotlin compiler to generate constructors that use default values.
 * Also, the `@NotBlank` annotation is targeted to the field, and not the constructor param.
 *
 * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html
 * @see https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets
 */
data class Variable @JvmOverloads constructor(@field:NotBlank val name: String,
                    var type: String? = null,
                    var description: String? = null,
                    var defaultValue: String? = null,
                    var editable: Boolean = true,
                    var mandatory: Boolean = false,
                    var validationRegex: String? = null)
