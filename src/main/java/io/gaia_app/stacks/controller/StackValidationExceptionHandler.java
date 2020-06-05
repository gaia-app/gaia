package io.gaia_app.stacks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class StackValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var message = ex.getBindingResult().getAllErrors().stream()
                .map(this::getMessage)
                .collect(Collectors.joining("\n"));

        return Map.of("message", message);
    }

    private String getMessage(ObjectError error){
        if(error instanceof FieldError){
            var fieldError = ((FieldError)error);
            return fieldError.getField() + " " + fieldError.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }
}
