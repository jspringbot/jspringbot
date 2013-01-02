package org.jspringbot.runner;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class RunnerPyObjectFactory {

    public static PyObject createRunnerPyObject(PythonInterpreter interpreter) {
        interpreter.exec("import robot; from robot.jarrunner import JarRunner");

        return interpreter.get("JarRunner");
    }
}
