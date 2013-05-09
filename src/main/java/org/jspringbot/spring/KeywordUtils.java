/*
 * Copyright (c) 2012. JSpringBot. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The JSpringBot licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jspringbot.spring;

import org.apache.commons.io.IOUtils;
import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;

import java.io.IOException;
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

        if(keywordInfo == null) {
            return "";
        }

        String desc = keywordInfo.description();

        if(desc.startsWith("classpath:")) {
            try {
                ResourceEditor editor = new ResourceEditor();
                editor.setAsText(desc);
                Resource r = (Resource) editor.getValue();

                return IOUtils.toString(r.getInputStream());
            } catch (Exception ignored) {
            }
        }

        return desc;
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
