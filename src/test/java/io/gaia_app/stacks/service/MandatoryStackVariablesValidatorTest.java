package io.gaia_app.stacks.service;

import io.gaia_app.modules.repository.TerraformModuleRepository;
import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.bo.Variable;
import io.gaia_app.stacks.bo.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MandatoryStackVariablesValidatorTest {

    @Mock
    private TerraformModuleRepository moduleRepository;

    @InjectMocks
    private MandatoryStackVariablesValidator validator;

    @Test
    void validator_shouldWork_whenModuleIsNull(){
        assertThat(validator.isValid(new Stack(), null)).isTrue();
    }

    @Test
    void validator_shouldReturnFalse_whenMandatoryVariableIsEmpty(){
        var variable = new Variable("test");
        variable.setMandatory(true);

        var module = new TerraformModule();
        module.setId("12");
        module.setVariables(List.of(variable));

        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        var stack = new Stack();
        stack.setModule(module);

        assertThat(validator.isValid(stack, null)).isFalse();
    }

    @Test
    void validator_shouldReturnFalse_whenMandatoryVariableIsBlank(){
        var variable = new Variable("test");
        variable.setMandatory(true);

        var module = new TerraformModule();
        module.setId("12");
        module.setVariables(List.of(variable));

        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        var stack = new Stack();
        stack.setModule(module);
        stack.setVariableValues(Map.of("test", "     "));

        assertThat(validator.isValid(stack, null)).isFalse();
    }

    @Test
    void validator_shouldReturnTrue_whenMandatoryVariableWithDefaultValueIsBlank(){
        var variable = new Variable("test");
        variable.setMandatory(true);
        variable.setDefaultValue("default");

        var module = new TerraformModule();
        module.setId("12");
        module.setVariables(List.of(variable));

        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        var stack = new Stack();
        stack.setModule(module);

        assertThat(validator.isValid(stack, null)).isTrue();
    }

    @Test
    void validator_shouldReturnTrue_whenMandatoryVariableIsNotBlank(){
        var variable = new Variable("test");
        variable.setMandatory(true);

        var module = new TerraformModule();
        module.setId("12");
        module.setVariables(List.of(variable));

        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        var stack = new Stack();
        stack.setModule(module);
        stack.setVariableValues(Map.of("test", "value"));

        assertThat(validator.isValid(stack, null)).isTrue();
    }

    @Test
    void validator_shouldReturnTrue_whenNonMandatoryVariableIsNull(){
        var variable = new Variable("test");

        var module = new TerraformModule();
        module.setId("12");
        module.setVariables(List.of(variable));

        when(moduleRepository.findById("12")).thenReturn(Optional.of(module));

        var stack = new Stack();
        stack.setModule(module);

        assertThat(validator.isValid(stack, null)).isTrue();
    }

}
