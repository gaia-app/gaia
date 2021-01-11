package io.gaia_app.stacks.bo;

/**
 * Represents the state of a Stack.
 */
public enum StackState {

    /**
     * When the stack is created but never applied
     */
    NEW,

    /**
     * When the stack has been applied
     */
    RUNNING,

    /**
     * When the stack is running, but need to be updated (after a variable or module change)
     */
    TO_UPDATE,

    /**
     * When the stack has been stopped
     */
    STOPPED,

    /**
     * When the stack is archived, and cannot be run anymore
     */
    ARCHIVED

}
