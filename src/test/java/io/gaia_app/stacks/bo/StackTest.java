package io.gaia_app.stacks.bo;

import io.gaia_app.modules.bo.TerraformModule;
import io.gaia_app.modules.bo.Variable;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void tfvars_shouldGenerateTfvarContents() {
        // given
        var variableWithDefault = new Variable("variableWithDefault", "string", "a variable with a default value", "myValue");
        var mandatoryVariable = new Variable("mandatoryVariable", "string", "a variable with a default value", null, true, true);

        var module = new TerraformModule();
        module.setVariables(List.of(variableWithDefault, mandatoryVariable));

        var stack = new Stack();
        stack.setModule(module);

        stack.setVariableValues(Map.of("mandatoryVariable", "myOtherValue"));

        // when
        var tfvars = stack.tfvars();

        // then
        assertThat(tfvars)
            .contains("variableWithDefault = \"myValue\"\n")
            .contains("mandatoryVariable = \"myOtherValue\"\n");
    }
}
