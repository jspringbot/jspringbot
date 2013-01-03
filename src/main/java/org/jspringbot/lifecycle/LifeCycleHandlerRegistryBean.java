package org.jspringbot.lifecycle;


import java.util.Map;

public class LifeCycleHandlerRegistryBean implements LifeCycleHandler {

    private LifeCycleHandler handler;

    public LifeCycleHandlerRegistryBean(LifeCycleHandler handler) {
        this.handler = handler;
    }

    public LifeCycleHandler getHandler() {
        return handler;
    }

    @Override
    public void startSuite(String name, Map attributes) {
        handler.startSuite(name, attributes);
    }

    @Override
    public void endSuite(String name, Map attributes) {
        handler.endSuite(name, attributes);
    }

    @Override
    public void startTest(String name, Map attributes) {
        handler.startTest(name, attributes);
    }

    @Override
    public void endTest(String name, Map attributes) {
        handler.endTest(name, attributes);
    }

    @Override
    public void startKeyword(String name, Map attributes) {
        handler.startKeyword(name, attributes);
    }

    @Override
    public void endKeyword(String name, Map attributes) {
        handler.endKeyword(name, attributes);
    }
}
