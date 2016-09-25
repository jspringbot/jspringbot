package org.jspringbot.runner;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.DisposableBean;

public class PythonInterpreterBean implements AutoCloseable, DisposableBean {

    private PythonInterpreter interpreter;

    public PythonInterpreterBean(PythonInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void setRecursionLimit(int recursionLimit) {
        interpreter.getSystemState().setrecursionlimit(recursionLimit);
    }

    public void close() throws Exception {
        interpreter.close();
    }

    public void destroy() throws Exception {
        close();
    }
}
