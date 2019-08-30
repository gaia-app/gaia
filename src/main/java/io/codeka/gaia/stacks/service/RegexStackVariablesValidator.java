package io.codeka.gaia.stacks.service;

import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.RegexStackVariablesValidation;
import io.codeka.gaia.stacks.bo.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Service
public class RegexStackVariablesValidator implements ConstraintValidator<RegexStackVariablesValidation, Stack> {

    @Autowired
    private TerraformModuleRepository moduleRepository;

    @Override
    public void initialize(RegexStackVariablesValidation constraintAnnotation) {
        // nothing to do here
    }

    @Override
    public boolean isValid(Stack stack, ConstraintValidatorContext constraintValidatorContext) {
        if(stack.getModuleId() == null || stack.getModuleId().isBlank()){
            // no module defined, so variables are valid !
            return true;
        }
        // getting the module
        var module = this.moduleRepository.findById(stack.getModuleId()).orElseThrow();

        return module.getVariables().stream()
                // not checking non regex values
                .filter(terraformVariable -> terraformVariable.getValidationRegex() != null)
                // not checking blank values (doing it first because Pair want non-null values)
                .filter(terraformVariable -> {
                    var value = stack.getVariableValues().get(terraformVariable.getName());
                    return value != null && ! value.isBlank();
                })
                // associating each variable with its value
                .map(terraformVariable -> Pair.of(terraformVariable, stack.getVariableValues().get(terraformVariable.getName())))
                .allMatch(pair -> {
                    var pattern = Pattern.compile(pair.getFirst().getValidationRegex());
                    return pattern.matcher(pair.getSecond()).matches();
                });
    }
}
