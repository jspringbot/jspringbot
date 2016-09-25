import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.SpringRobotLibrary;

public class JSpringBotSuite extends SpringRobotLibrary {
    public static final String ROBOT_LIBRARY_SCOPE = RobotScope.SUITE.getValue();

    public JSpringBotSuite() throws Exception {
        super("spring/jspringbot-suite.xml");
    }

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     * @throws Exception on error
     */
    public JSpringBotSuite(String springConfigPath) throws Exception {
        super(springConfigPath);
    }
}
