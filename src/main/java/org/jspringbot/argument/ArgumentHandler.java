package org.jspringbot.argument;

public interface ArgumentHandler {

    boolean isSupported(String keyword, Object parameter);

    Object handle(Object parameter);
}
