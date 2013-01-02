package org.jspringbot.runner;


import org.robotframework.RobotRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRobotFramework {
    public static ClassPathXmlApplicationContext CONTEXT;

    public static void main(String[] args) {
        int rc = run(args);
        System.exit(rc);
    }

    public static int run(String[] args) {
        CONTEXT = new ClassPathXmlApplicationContext("classpath:robot-runner.xml");

        try {
            RobotRunner runner = CONTEXT.getBean(RobotRunner.class);

            return runner.run(args);
        } finally {
            CONTEXT.destroy();
        }
    }
}
