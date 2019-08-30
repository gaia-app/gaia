package io.codeka.gaia.stacks.service;

import io.codeka.gaia.modules.bo.TerraformVariable;
import io.codeka.gaia.modules.repository.TerraformModuleRepository;
import io.codeka.gaia.stacks.bo.Stack;
import io.codeka.gaia.stacks.bo.MandatoryStackVariablesValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class MandatoryStackVariablesValidator implements ConstraintValidator<MandatoryStackVariablesValidation, Stack> {

    @Autowired
    private TerraformModuleRepository moduleRepository;

    @Override
    public void initialize(MandatoryStackVariablesValidation constraintAnnotation) {
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
                .filter(TerraformVariable::isMandatory)
                .allMatch(variable -> {
                    var variableValue = stack.getVariableValues().get(variable.getName());
                    return variableValue != null && !variableValue.isBlank();
                });
    }
}
