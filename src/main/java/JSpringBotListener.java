import org.jspringbot.MainContextHolder;
import org.jspringbot.Visitor;
import org.jspringbot.lifecycle.LifeCycleHandlerManager;
import org.jspringbot.runner.RobotListenerInterface;
import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.SpringRobotLibraryManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class JSpringBotListener implements RobotListenerInterface {
    @Override
    public void startSuite(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).startSuite(name, attributes);
            }
        });
    }

    @Override
    public void endSuite(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).endSuite(name, attributes);
            }
        });
    }

    @Override
    public void startTest(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).startTest(name, attributes);
            }
        });
    }

    @Override
    public void endTest(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).endTest(name, attributes);
            }
        });
    }

    @Override
    public void startKeyword(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).startKeyword(name, attributes);
            }
        });
    }

    @Override
    public void endKeyword(final String name, final Map attributes) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).endKeyword(name, attributes);
            }
        });
    }

    @Override
    public void logMessage(final Map message) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).logMessage(message);
            }
        });
    }

    @Override
    public void message(final Map message) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).message(message);
            }
        });
    }

    @Override
    public void outputFile(final String path) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).outputFile(path);
            }
        });
    }

    @Override
    public void logFile(final String path) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).logFile(path);
            }
        });
    }

    @Override
    public void reportFile(final String path) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).reportFile(path);
            }
        });
    }

    @Override
    public void debugFile(final String path) {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).debugFile(path);
            }
        });
    }

    @Override
    public void close() {
        SpringRobotLibraryManager manager = MainContextHolder.get().getBean(SpringRobotLibraryManager.class);
        manager.visitActive(RobotScope.ALL, new Visitor<ClassPathXmlApplicationContext>() {
            @Override
            public void visit(ClassPathXmlApplicationContext context) {
                new LifeCycleHandlerManager(context).close();
            }
        });
    }
}
