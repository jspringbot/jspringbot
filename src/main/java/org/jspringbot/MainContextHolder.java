package org.jspringbot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainContextHolder {
    private static final ThreadLocal<ClassPathXmlApplicationContext> CONTEXT = new ThreadLocal<ClassPathXmlApplicationContext>();

    public static ClassPathXmlApplicationContext create() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:robot-runner.xml");
        CONTEXT.set(context);

        return context;
    }

    public static ClassPathXmlApplicationContext get() {
        return CONTEXT.get();
    }

    public static void remove() {
        get().destroy();
        CONTEXT.remove();
    }
}
