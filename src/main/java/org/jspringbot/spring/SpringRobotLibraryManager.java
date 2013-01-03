package org.jspringbot.spring;

import org.jspringbot.Visitor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface SpringRobotLibraryManager {

    void addLibrary(Class libraryClass, ClassPathXmlApplicationContext context) throws Exception;

    void visitActive(RobotScope scope, Visitor<ClassPathXmlApplicationContext> visitor);
}
