package org.jspringbot;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.python.core.*;
import org.springframework.aop.framework.ProxyFactory;

import java.util.*;

public class PythonUtils {
    @SuppressWarnings("unchecked")
    public static Object toJava(Object arg) {
        if(List.class.isInstance(arg)) {
            return proxy(List.class, (List) arg);
        } else if(Set.class.isInstance(arg)) {
            return proxy(Set.class, (Set) arg);
        } else if(Map.class.isInstance(arg)) {
            return proxy(Map.class, (Map) arg);
        } else if(PyObject.class.isInstance(arg)) {
            return ((PyObject) arg).__tojava__(Object.class);
        } else if(PyNone.class.isInstance(arg)) {
            return null;
        } else {
            return arg;
        }
    }

    public static <T> Object proxy(Class<T> clazz, T object) {
        return new PyProxyFactory<T>(clazz, object).getProxy();
    }

    private static class PyProxyFactory<T> implements MethodInterceptor {

        private T delegate;

        private Class<T> clazz;

        private PyProxyFactory(Class<T> clazz, T delegate) {
            this.clazz = clazz;
            this.delegate = delegate;
        }

        public T getProxy() {
            return ProxyFactory.getProxy(clazz, this);
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object returnedValue = invocation.getMethod().invoke(delegate, invocation.getArguments());

            return toJava(returnedValue);
        }
    }
}
