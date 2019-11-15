package io.codeka.gaia.stacks.service

import io.codeka.gaia.modules.bo.Variable
import io.codeka.gaia.modules.repository.TerraformModuleRepository
import io.codeka.gaia.stacks.bo.RegexStackVariablesValidation
import io.codeka.gaia.stacks.bo.Stack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Service
class RegexStackVariablesValidator(@Autowired val moduleRepository: TerraformModuleRepository)
    : ConstraintValidator<RegexStackVariablesValidation, Stack> {

    override fun isValid(stack: Stack, constraintValidatorContext: ConstraintValidatorContext?): Boolean {
        // we do not validate anything if the stacks has no module reference (it will send a BadRequest anyway)
        if( stack.moduleId.isNullOrBlank()) {
            return true
        }
        // getting the module, returning true if module cannot be found !
        // this should (let's hope so) never happen ! another validator should handle this case
        val module = this.moduleRepository.findByIdOrNull(stack.moduleId) ?: return true

        return module.variables
                // not checking non regex values
                .filter { ! it.validationRegex.isNullOrBlank() }
                // not checking blank values (doing it first because Pair want non-null values)
                .filter { ! stack.variableValues[it.name].isNullOrBlank() }
                // validate the pattern (using non null assert as variable value is not more null here)
                .all { it.validateWithPattern(stack.variableValues[it.name]!!) }
    }
}

private fun Variable.validateWithPattern(value: String): Boolean {
    val pattern = Pattern.compile(this.validationRegex)
    return pattern.matcher(value).matches()
}
