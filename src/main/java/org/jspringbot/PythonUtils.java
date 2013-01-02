package org.jspringbot;

import org.python.core.*;

public class PythonUtils {
    public static Object toJava(Object arg) {
        if(PyUnicode.class.isInstance(arg)) {
            return ((PyUnicode) arg).getString();
        } else if(PyBoolean.class.isInstance(arg)) {
            return ((PyBoolean) arg).getBooleanValue();
        } else if(PyInteger.class.isInstance(arg)) {
            return ((PyInteger) arg).getValue();
        } else if(PyFloat.class.isInstance(arg)) {
            return ((PyFloat) arg).getValue();
        } else if(PyLong.class.isInstance(arg)) {
            return ((PyLong) arg).getValue();
        } else if(PyString.class.isInstance(arg)) {
            return ((PyString) arg).getString();
        } else if(PyArray.class.isInstance(arg)) {
            return ((PyArray) arg).getArray();
        } else {
            return arg;
        }
    }
}
