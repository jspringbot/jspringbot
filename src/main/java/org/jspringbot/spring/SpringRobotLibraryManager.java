package org.jspringbot.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface SpringRobotLibraryManager {

    void addLibrary(Class libraryClass, ClassPathXmlApplicationContext context) throws Exception;

    <T> void visitActiveBeanOfType(RobotScope scope, BeanOfTypeVisitor<T> visitor, Class<T> beanType);
}
