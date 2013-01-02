package org.jspringbot.lifecycle;

import java.util.Map;

public interface LifeCycleHandler {
    void startSuite(String name, Map attributes);

    void endSuite(String name, Map attributes);

    void startTest(String name, Map attributes);

    void endTest(String name, Map attributes);

    void startKeyword(String name, Map attributes);

    void endKeyword(String name, Map attributes);
}
