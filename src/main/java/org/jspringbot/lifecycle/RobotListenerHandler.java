package org.jspringbot.lifecycle;

import java.util.Map;

public interface RobotListenerHandler extends LifeCycleHandler {
    void logMessage(Map message);

    void message(Map message);

    void outputFile(String path);

    void logFile(String path);

    void reportFile(String path);

    void debugFile(String path);

    void close();
}
