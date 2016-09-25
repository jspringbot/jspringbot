import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.RobotScope;
import org.jspringbot.spring.SpringRobotLibrary;

public class JSpringBotGlobal extends SpringRobotLibrary {
    public static final String ROBOT_LIBRARY_SCOPE = RobotScope.GLOBAL.getValue();

    public JSpringBotGlobal() throws Exception {
        super("spring/jspringbot-global.xml");
    }

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     * @throws Exception on error
     */
    public JSpringBotGlobal(String springConfigPath) throws Exception {
        super(springConfigPath);
    }
}
