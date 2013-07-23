package org.jspringbot.log4j;

import org.apache.log4j.Logger;
import org.jspringbot.lifecycle.RobotListenerHandler;

import java.util.Map;

public class Log4jLifeCycleHandler implements RobotListenerHandler {

    private static final Logger LOGGER = Logger.getLogger(Log4jLifeCycleHandler.class);

    @Override
    public void logMessage(Map message) {
    }

    @Override
    public void message(Map message) {
    }

    @Override
    public void outputFile(String path) {
    }

    @Override
    public void logFile(String path) {
    }

    @Override
    public void reportFile(String path) {
    }

    @Override
    public void debugFile(String path) {
    }

    @Override
    public void close() {
    }

    @Override
    public void startSuite(String name, Map attributes) {
    }

    @Override
    public void endSuite(String name, Map attributes) {
    }

    @Override
    public void startTest(String name, Map attributes) {
    }

    @Override
    public void endTest(String name, Map attributes) {
    }

    @Override
    public void startKeyword(String name, Map attributes) {
        LOGGER.info(String.format("Starting keyword: %s with parameter(s): %s", name, attributes.get("args")));
    }

    @Override
    public void endKeyword(String name, Map attributes) {
    }

    @Override
    public void startJSpringBotKeyword(String name, Map attributes) {
    }

    @Override
    public void endJSpringBotKeyword(String name, Map attributes) {
    }
}
