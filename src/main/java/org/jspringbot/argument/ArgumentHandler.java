package org.jspringbot.argument;

public interface ArgumentHandler {

    boolean isSupported(Object parameter);

    Object handle(Object parameter);
}
