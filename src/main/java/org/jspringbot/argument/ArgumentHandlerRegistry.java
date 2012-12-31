package org.jspringbot.argument;

import java.util.LinkedList;
import java.util.List;

public final class ArgumentHandlerRegistry {
    public static final ArgumentHandlerRegistry REGISTRY = new ArgumentHandlerRegistry();

    private List<ArgumentHandler> handlers = new LinkedList<ArgumentHandler>();

    private ArgumentHandlerRegistry() {}

    public void register(ArgumentHandler handler) {
        handlers.add(handler);
    }

    private Object handle(Object arg) {
        try {
            for(ArgumentHandler handler : handlers) {
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
