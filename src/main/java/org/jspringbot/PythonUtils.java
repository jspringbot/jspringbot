package org.jspringbot;

import org.python.core.*;

import java.util.*;

public class PythonUtils {
    @SuppressWarnings("unchecked")
    public static Object toJava(Object arg) {
        if(PyList.class.isInstance(arg)) {
            PyObject[] pyObjs = ((PyList) arg).getArray();
            List<Object> list = new ArrayList<Object>(pyObjs.length);

            for(PyObject pyObj : pyObjs) {
                list.add(toJava(pyObj));
            }

            return list;
        } else if(PyDictionary.class.isInstance(arg)) {
            PyDictionary pyMap = (PyDictionary) arg;
            Map map = new HashMap();

            for(Map.Entry entry : (Set<Map.Entry>) pyMap.entrySet()) {
                map.put(toJava(entry.getKey()), toJava(entry.getValue()));
            }

            return map;
        } else if(PyObject.class.isInstance(arg)) {
            return ((PyObject) arg).__tojava__(Object.class);
        } else {
            return arg;
        }
    }
}
