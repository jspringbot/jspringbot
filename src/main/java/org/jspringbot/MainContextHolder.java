package org.jspringbot;

import org.jspringbot.spring.ApplicationContextFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainContextHolder {
    private static final ThreadLocal<ClassPathXmlApplicationContext> CONTEXT = new ThreadLocal<ClassPathXmlApplicationContext>();

    public static ClassPathXmlApplicationContext create() {
        ClassPathXmlApplicationContext context = ApplicationContextFactory.create("classpath:spring-main.xml");
        CONTEXT.set(context);

        return context;
    }

    public static boolean isEnabled() {
        return CONTEXT.get() != null;
    }

    public static ClassPathXmlApplicationContext get() {
        if(CONTEXT.get() == null) {
            throw new IllegalStateException("Not running in jspringbot runtime.");
        }

        return CONTEXT.get();
    }

    public static void remove() {
        get().destroy();
        CONTEXT.remove();
    }
}
