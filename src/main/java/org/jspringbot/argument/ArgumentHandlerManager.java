package org.jspringbot.argument;

import org.springframework.context.ApplicationContext;

import java.util.Map;

public final class ArgumentHandlerManager {
    private ApplicationContext context;

    public ArgumentHandlerManager(ApplicationContext context) {
        this.context = context;
    }

    private Object handle(String keyword, Object arg) {
        try {
            Map<String, ArgumentHandlerRegistryBean> handlers = context.getBeansOfType(ArgumentHandlerRegistryBean.class);
            for(ArgumentHandlerRegistryBean handler : handlers.values()) {
                if(handler.isSupported(keyword, arg)) {
                    return handler.handle(arg);
                }
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
            throw new IllegalArgumentException(String.format("Unable to handle arguments '%s'", arg), e);
        }

        return arg;
    }

    public Object[] handlerArguments(String keyword, Object[] args) {
        if(args == null || args.length == 0) {
            return args;
        }

        Object[] converted = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            converted[i] = handle(keyword, args[i]);
        }

        return converted;
    }
}
