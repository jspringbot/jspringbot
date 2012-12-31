package org.jspringbot.spring;

import org.springframework.context.ApplicationContext;

public final class ApplicationContextHolder {

    private static ThreadLocal<ApplicationContext> THREAD_LOCAL = new ThreadLocal<ApplicationContext>();

    static void set(ApplicationContext context) {
        THREAD_LOCAL.set(context);
    }

    public static ApplicationContext get() {
        return THREAD_LOCAL.get();
    }

    static void remove() {
        THREAD_LOCAL.remove();
    }
}
