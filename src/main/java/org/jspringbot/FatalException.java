package org.jspringbot;

/**
 * Created by IntelliJ IDEA.
 * User: shiela
 * Date: 9/27/11
 * To change this template use File | Settings | File Templates.
 */
public class FatalException extends RuntimeException {
    public static final boolean ROBOT_EXIT_ON_FAILURE = true;

    public FatalException(String message) {
        super(message);
    }
}
