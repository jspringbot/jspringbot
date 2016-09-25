package org.jspringbot.runner;


import org.jspringbot.MainContextHolder;
import org.robotframework.RobotPythonRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRobotFramework {
    public static void main(String[] args) {
        int rc = run(args);
        System.exit(rc);
    }

    public static int run(String[] args) {
        try {
            ClassPathXmlApplicationContext context = MainContextHolder.create();

            RobotPythonRunner runner = context.getBean(RobotPythonRunner.class);

            return runner.run(args);
        } catch (RuntimeException e) {
            e.printStackTrace();

            throw e;
        } finally {
            MainContextHolder.remove();
        }
    }
}
