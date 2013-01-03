package org.jspringbot.lifecycle;

import org.jspringbot.Visitor;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class LifeCycleHandlerManager implements LifeCycleHandler {
    private ApplicationContext context;

    public LifeCycleHandlerManager(ApplicationContext context) {
        this.context = context;
    }

    private void visitAll(Visitor<LifeCycleHandler> visitor) {
        try {
            Map<String, LifeCycleHandlerRegistryBean> handlers = context.getBeansOfType(LifeCycleHandlerRegistryBean.class);
            for(LifeCycleHandlerRegistryBean handler : handlers.values()) {
                visitor.visit(handler);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void startSuite(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.startSuite(name, attributes);
            }
        });
    }

    @Override
    public void endSuite(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.endSuite(name, attributes);
            }
        });
    }

    @Override
    public void startTest(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.startTest(name, attributes);
            }
        });
    }

    @Override
    public void endTest(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.endTest(name, attributes);
            }
        });
    }

    @Override
    public void startKeyword(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.startKeyword(name, attributes);
            }
        });
    }

    @Override
    public void endKeyword(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.endKeyword(name, attributes);
            }
        });
    }

    @Override
    public void startJSpringBotKeyword(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.startJSpringBotKeyword(name, attributes);
            }
        });
    }

    @Override
    public void endJSpringBotKeyword(final String name, final Map attributes) {
        visitAll(new Visitor<LifeCycleHandler>() {
            @Override
            public void visit(LifeCycleHandler handler) {
                handler.endJSpringBotKeyword(name, attributes);
            }
        });
    }
}
