package org.jspringbot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface MainContextVisitor {
    void visit(ClassPathXmlApplicationContext context);
}
