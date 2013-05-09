package org.jspringbot.runner;

import org.python.util.PythonInterpreter;

public class PythonInterpreterBean {

    private PythonInterpreter interpreter;

    public PythonInterpreterBean(PythonInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void setRecursionLimit(int recursionLimit) {
        interpreter.getSystemState().setrecursionlimit(recursionLimit);
    }
}
