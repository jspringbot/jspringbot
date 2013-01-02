import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.SpringRobotLibrary;

public class JSpringBotTest extends SpringRobotLibrary {
    public static final String ROBOT_LIBRARY_SCOPE = RobotScope.TEST.getValue();

    public JSpringBotTest() throws Exception {
        super("spring/jspringbot-test.xml");
    }

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     */
    public JSpringBotTest(String springConfigPath) throws Exception {
        super(springConfigPath);
    }
}
