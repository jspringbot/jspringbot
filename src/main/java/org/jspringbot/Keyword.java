package org.jspringbot;

/**
 * Interface that all keywords should implement.
 * {@link org.jspringbot.spring.SpringRobotLibrary} determines implemented keywords by scanning
 * the spring context for beans implementing this interface.
 */
public interface Keyword {

    /**
     * Executes the keyword.
     *
     * @param params keyword arguments; Robot Framework passes empty array (not null) when no arguments are passed.
     * @return result of the keyword execution.
     */
    Object execute(Object[] params) throws Exception;
}