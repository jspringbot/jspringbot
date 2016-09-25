package org.jspringbot.runner;

import org.python.util.PythonInterpreter;

public class PythonInterpreterBean implements AutoCloseable {

    private PythonInterpreter interpreter;

    public PythonInterpreterBean(PythonInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void setRecursionLimit(int recursionLimit) {
        interpreter.getSystemState().setrecursionlimit(recursionLimit);
    }

    public void close() throws Exception {
        interpreter.cleanup();
    }
}
