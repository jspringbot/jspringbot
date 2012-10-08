package org.jspringbot;

public class JSpringBotLogger {

    public static JSpringBotLogger getLogger(Class logger) {
        return new JSpringBotLogger(logger);
    }

    protected Class logger;

    protected JSpringBotLogger(Class logger) {
        this.logger = logger;
    }

    public void info(String msg, Object... args) {
        log("INFO", msg, args);
    }

    public void warn(String msg, Object... args) {
        log("WARN", msg, args);
    }

    public void trace(String msg, Object... args) {
        log("TRACE", msg, args);
    }

    public void debug(String msg, Object... args) {
        log("DEBUG", msg, args);
    }

    public void html(String msg, Object... args) {
        log("HTML", msg, args);
    }

    public void pureHtml(String msg, Object... args) {
        if(args != null && args.length > 0) {
            System.out.println(String.format("*HTML* %s", String.format(msg, args)));
        } else {
            System.out.println(String.format("*HTML* %s", msg));
        }
    }

    public void log(String level, String msg, Object... args) {
        if(args != null && args.length > 0) {
            System.out.println(String.format("*%s* [%s] %s", level, logger.getSimpleName(), String.format(msg, args)));
        } else {
            System.out.println(String.format("*%s* [%s] %s", level, logger.getSimpleName(), msg));
        }
    }
}
