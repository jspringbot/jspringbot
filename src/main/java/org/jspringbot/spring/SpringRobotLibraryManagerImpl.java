package org.jspringbot.spring;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpringRobotLibraryManagerImpl implements SpringRobotLibraryManager {

    private Map<RobotScope, ClassPathXmlApplicationContext> libraries = new LinkedHashMap<RobotScope, ClassPathXmlApplicationContext>();

    @Override
    public void addLibrary(Class libraryClass, ClassPathXmlApplicationContext context) throws Exception {
        Field field = ReflectionUtils.findField(libraryClass, "ROBOT_LIBRARY_SCOPE");

        RobotScope scope = RobotScope.toRobotScope((String) field.get(libraryClass));

        ClassPathXmlApplicationContext current = libraries.get(scope);
        if(current != null && current.isActive()) {
            current.destroy();
        } else {
            libraries.put(scope, context);
        }
    }

    @Override
    public <T> void visitActiveBeanOfType(RobotScope scope, BeanOfTypeVisitor<T> visitor, Class<T> beanType) {
        if(RobotScope.ALL.equals(scope)) {
            for(ClassPathXmlApplicationContext context : libraries.values()) {
                if(context.isActive() && context.isRunning()) {
                    visitor.visit(context.getBeansOfType(beanType));
                }
            }

            return;
        }

        ClassPathXmlApplicationContext context = libraries.get(scope);
        if(context != null && context.isActive() && context.isRunning()) {
            visitor.visit(context.getBeansOfType(beanType));
        }
    }
}
