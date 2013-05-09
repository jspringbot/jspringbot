package org.jspringbot.runner;

import org.python.core.PyObject;
import org.robotframework.RobotRunner;

public class RobotRunnerFactory {
    public static RobotRunner createRobotRunner(PyObject robotRunnerClass) {
        PyObject runnerObject = robotRunnerClass.__call__();
        return (RobotRunner) runnerObject.__tojava__(RobotRunner.class);
    }
}
