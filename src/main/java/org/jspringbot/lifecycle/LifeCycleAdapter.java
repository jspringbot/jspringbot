package org.jspringbot.lifecycle;

import java.util.Map;

public abstract class LifeCycleAdapter implements LifeCycleHandler {
    @Override
    public void startSuite(String name, Map attributes) {}

    @Override
    public void endSuite(String name, Map attributes) {}

    @Override
    public void startTest(String name, Map attributes) {}

    @Override
    public void endTest(String name, Map attributes) {}

    @Override
    public void startKeyword(String name, Map attributes) {}

    @Override
    public void endKeyword(String name, Map attributes) {}

    @Override
    public void startJSpringBotKeyword(String name, Map attributes) {}

    @Override
    public void endJSpringBotKeyword(String name, Map attributes) {}
}
