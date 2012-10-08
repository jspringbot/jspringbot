package org.jspringbot;

public class ContinuableException extends RuntimeException {
    public static final boolean ROBOT_CONTINUE_ON_FAILURE = true;

    public ContinuableException(String message) {
        super(message);
    }
}