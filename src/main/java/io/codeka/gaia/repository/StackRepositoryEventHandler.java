package io.codeka.gaia.repository;

import io.codeka.gaia.bo.Stack;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.UUID;

@RepositoryEventHandler
public class StackRepositoryEventHandler {

    @HandleBeforeCreate
    public void handleStackCreate(Stack stack){
        // generate an id for new stacks
        if(stack.getId() == null){
            stack.setId(UUID.randomUUID().toString());
        }
    }
}
