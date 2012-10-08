package org.jspringbot;

/**
 * Dynamic robot library interface.
 */
public interface DynamicRobotLibrary {

    /**
     * Implements the Robot Framework interface method 'get_keyword_names' required for dynamic libraries.
     *
     * @return String array containing all keyword names defined in library.
     */
    String[] getKeywordNames();

    /**
     * Robot Framework interface method 'run_keyword' required for dynamic libraries.
     *
     * @param keyword name of keyword to be executed
     * @param params parameters passed by Robot Framework
     * @return result of the keyword execution
     */
    Object runKeyword(String keyword, Object[] params);

    /**
     * Retrieves the documentation for the given keyword.
     *
     * @param keyword robot keyword
     * @return keyword description
     */
    String getKeywordDocumentation(String keyword);

    /**
     * Retrieves parameter description for the given keyword.
     *
     * @param keyword robot keyword
     * @return parameter description
     */
    String[] getKeywordArguments(String keyword);
}

