package org.jspringbot.spring;

/**
 * Test suite scoped spring robot library.
 */
public class TestSuiteSpringLibrary extends SpringRobotLibrary {

    /** Setting library scope to GLOBAL. */
    public static final String ROBOT_LIBRARY_SCOPE = "TEST SUITE";

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     */
    public TestSuiteSpringLibrary(String springConfigPath) {
        super(springConfigPath);
    }
}
