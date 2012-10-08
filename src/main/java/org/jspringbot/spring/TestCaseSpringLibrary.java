package org.jspringbot.spring;

/**
 * Test case scoped spring robot library.
 */
public class TestCaseSpringLibrary extends SpringRobotLibrary {

    /** Setting library scope to GLOBAL. */
    public static final String ROBOT_LIBRARY_SCOPE = "TEST CASE";

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     */
    public TestCaseSpringLibrary(String springConfigPath) {
        super(springConfigPath);
    }
}
