package org.jspringbot.runner;

import org.python.core.PyObject;
import org.robotframework.RobotPythonRunner;
import org.robotframework.RobotRunner;

public class RobotRunnerFactory {

    public static RobotPythonRunner createRobotRunner(PyObject robotRunnerClass) {
        PyObject runnerObject = robotRunnerClass.__call__();
        return (RobotPythonRunner) runnerObject.__tojava__(RobotPythonRunner.class);
    }
}
