package org.jspringbot.spring;

/**
 * Global scoped spring robot library.
 */
public class GlobalSpringLibrary extends SpringRobotLibrary {

    /** Setting library scope to GLOBAL. */
    public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     */
    public GlobalSpringLibrary(String springConfigPath) {
        super(springConfigPath);
    }
}
