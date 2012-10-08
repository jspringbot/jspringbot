package org.jspringbot.spring;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for robot keyword metadata retrieval.
 */
public class KeywordUtils {

    /**
     * Default argument descriptor for all keywords not annotated with KeywordInfo.
     * Or for annotated ones that did not specify parameter descriptor.
     */
    public static final String[] DEFAULT_PARAMS = {"None"};

    /**
     * Private constructor to disable creation of KeywordUtils objects.
     */
    private KeywordUtils() {
        // Disable creation of new instance.
    }

    /**
     * Builds mapping of robot keywords to actual bean names in spring context.
     *
     * @param context Spring application context.
     * @return mapping of robot keywords to bean names
     */
    public static Map<String, String> getKeywordMap(ApplicationContext context) {
        Map<String, String> keywordToBeanMap = new HashMap<String, String>();

        // Retrieve beans implementing the Keyword interface.
        String[] beanNames = context.getBeanNamesForType(Keyword.class);

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);

            // Retrieve keyword information
            KeywordInfo keywordInfo = AnnotationUtils.findAnnotation(bean.getClass(), KeywordInfo.class);

            // Set keyword name as specified in the keyword info, or if information is not available, use bean name.
            String keywordName = keywordInfo != null ? keywordInfo.name() : beanName;

            if (keywordToBeanMap.put(keywordName, beanName) != null) {
                // If map already contains the keyword name, throw an exception. Keywords should be unique.
                throw new RuntimeException("Multiple definitions for keyword '" + keywordName + "' exists.");
            }
        }

        return keywordToBeanMap;
    }


    /**
     * Retrieves the given keyword's description.
     *
     * @param keyword keyword name
     * @param context Spring application context
     * @param beanMap keyword name to bean name mapping
     * @return the documentation string of the given keyword, or empty string if unavailable.
     */
    public static String getDescription(String keyword, ApplicationContext context, Map<String, String> beanMap) {
        KeywordInfo keywordInfo = getKeywordInfo(keyword, context, beanMap);

        return keywordInfo == null ? "" : keywordInfo.description();
    }

    /**
     * Retrieves the given keyword's parameter description.
     *
     * @param keyword keyword name
     * @param context Spring application context
     * @param beanMap keyword name to bean name mapping
     * @return the parameter description string of the given keyword, or {"*args"} if unavailable.
     */
    public static String[] getParameters(String keyword, ApplicationContext context, Map<String, String> beanMap) {
        KeywordInfo keywordInfo = getKeywordInfo(keyword, context, beanMap);

        return keywordInfo == null ? DEFAULT_PARAMS : keywordInfo.parameters();
    }

    /**
     * Retrieves the KeywordInfo of the given keyword.
     *
     * @param keyword keyword name
     * @param context Spring application context
     * @param beanMap keyword name to bean name mapping
     * @return KeywordInfo object or null if unavailable
     */
    public static KeywordInfo getKeywordInfo(String keyword, ApplicationContext context, Map<String, String> beanMap) {
        Object bean = context.getBean(beanMap.get(keyword));
        return AnnotationUtils.findAnnotation(bean.getClass(), KeywordInfo.class);
    }
}
