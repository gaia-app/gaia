package io.gaia_app.stacks.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StackValidationExceptionHandlerTest {

    @Mock
    private BindingResult bindingResult;

    private StackValidationExceptionHandler handler = new StackValidationExceptionHandler();

    @Test
    void shouldJoinAllErrorMessages() {
        // given
        var objectError = new ObjectError("stack", "mandatory variables should not be null");
        var fieldError = new FieldError("stack", "name", "should not be blank");
        when(bindingResult.getAllErrors()).thenReturn(List.of(objectError, fieldError));

        // when
        var result = handler.handleValidationExceptions(new MethodArgumentNotValidException(null, bindingResult));

        // then
        assertThat(result).containsEntry("message", "mandatory variables should not be null\nname should not be blank");
    }
}
