package org.jspringbot.runner;


import org.jspringbot.MainContextHolder;
import org.robotframework.RobotRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRobotFramework {
    public static void main(String[] args) {
        int rc = run(args);
        System.exit(rc);
    }

    public static int run(String[] args) {
        try {
            ClassPathXmlApplicationContext context = MainContextHolder.get();

            RobotRunner runner = context.getBean(RobotRunner.class);

            return runner.run(args);
        } finally {
            MainContextHolder.remove();
        }
    }
}
