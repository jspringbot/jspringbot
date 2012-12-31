package org.jspringbot.argument;

import org.springframework.context.ApplicationContext;

import java.util.Map;

public final class ArgumentHandlerManager {
    private Map<String, ArgumentHandlerRegistryBean> handlers;

    public ArgumentHandlerManager(ApplicationContext context) {
         handlers = context.getBeansOfType(ArgumentHandlerRegistryBean.class);
    }

    private Object handle(Object arg) {
        try {
            for(ArgumentHandlerRegistryBean handler : handlers.values()) {
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
