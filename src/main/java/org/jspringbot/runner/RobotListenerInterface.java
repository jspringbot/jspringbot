package org.jspringbot.runner;

public interface RobotListenerInterface {
    public static final int ROBOT_LISTENER_API_VERSION = 2;

    void startSuite(String name, java.util.Map attributes);
    void endSuite(String name, java.util.Map attributes);
    void startTest(String name, java.util.Map attributes);
    void endTest(String name, java.util.Map attributes);
    void startKeyword(String name, java.util.Map attributes);
    void endKeyword(String name, java.util.Map attributes);
    void logMessage(java.util.Map message);
    void message(java.util.Map message);
    void outputFile(String path);
    void logFile(String path);
    void reportFile(String path);
    void debugFile(String path);
    void close();
}