package org.jspringbot.argument;

public class ArgumentHandlerRegistryBean {

    public ArgumentHandlerRegistryBean(ArgumentHandler handler) {
        ArgumentHandlerRegistry.REGISTRY.register(handler);
    }
}
