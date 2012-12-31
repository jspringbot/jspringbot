package org.jspringbot.argument;

import org.springframework.context.ApplicationContext;

import java.util.Map;

public final class ArgumentHandlers {
    private Map<String, ArgumentHandler> handlers;

    public ArgumentHandlers(ApplicationContext context) {
         handlers = context.getBeansOfType(ArgumentHandler.class);
    }

    private Object handle(Object arg) {
        try {
            for(ArgumentHandler handler : handlers.values()) {
                if(handler.isSupported(arg)) {
                    return handler.handle(arg);
                }
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        return arg;
    }

    public Object[] handlerArguments(Object[] args) {
        if(args == null || args.length == 0) {
            return args;
        }

        Object[] converted = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            converted[i] = handle(args[i]);
        }

        return converted;
    }
}
