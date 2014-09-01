package org.jspringbot.log4j;

import org.apache.log4j.Logger;
import org.jspringbot.lifecycle.LifeCycleAdapter;

import java.util.Map;

public class Log4jLifeCycleHandler extends LifeCycleAdapter {

    private static final Logger LOGGER = Logger.getLogger(Log4jLifeCycleHandler.class);

    @Override
    public void startKeyword(String name, Map attributes) {
        LOGGER.info(String.format("Starting keyword: %s with parameter(s): %s", name, attributes.get("args")));
    }

}
