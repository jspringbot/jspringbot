package org.jspringbot.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {
    private static final String PARENT_CONTEXT_KEY = "piraso.context";

    private static ApplicationContext PARENT_CONTEXT_REF;

    private static Boolean PIRASO_ENABLED;

    private static Class CONTEXT_LOGGER_BEAN_PROCESSOR_CLASS;

    public static synchronized ClassPathXmlApplicationContext create(String... resource) {
        try {
            if(isEnabled()) {
                return new PirasoClassPathXmlApplicationContext(resource);
            }
        } catch(Exception ignored) {
        }

        return new ClassPathXmlApplicationContext(resource);
    }

    private static synchronized boolean isEnabled() {
        if(PIRASO_ENABLED != null) {
            return PIRASO_ENABLED;
        }

        try {
            CONTEXT_LOGGER_BEAN_PROCESSOR_CLASS = Class.forName("org.piraso.server.ContextLoggerBeanProcessor");
            BeanFactoryLocator locator = ContextSingletonBeanFactoryLocator.getInstance();
            PARENT_CONTEXT_REF = (ApplicationContext) locator.useBeanFactory(PARENT_CONTEXT_KEY);

            return PARENT_CONTEXT_REF != null;
        } catch (Exception e) {
            PIRASO_ENABLED = false;

            return false;
        }
    }

    private static class PirasoClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
        public PirasoClassPathXmlApplicationContext(String... configLocations) throws BeansException {
            super(configLocations, PARENT_CONTEXT_REF);
        }

        @Override
        protected DefaultListableBeanFactory createBeanFactory() {
            DefaultListableBeanFactory factory = super.createBeanFactory();

            if(getParent() != null) {
                String[] postProcessorNames = getParent().getBeanNamesForType(CONTEXT_LOGGER_BEAN_PROCESSOR_CLASS, true, false);

                for (String ppName : postProcessorNames) {
                    BeanPostProcessor pp = getParent().getBean(ppName, BeanPostProcessor.class);
                    factory.addBeanPostProcessor(pp);
                }
            }

            return factory;
        }
    }
}
