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

import org.jspringbot.DynamicRobotLibrary;
import org.jspringbot.Keyword;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Robot Library that uses Spring application context.
 *
 * Searches keywords by looking for classes implementing the {@link org.jspringbot.Keyword} interface.
 * Keyword information (name, documentation and parameter description) are acquired from
 * {@link org.jspringbot.KeywordInfo} annotation on keyword implementations. In the absence of keyword information,
 * keyword name is equivalent to bean name while keyword documentation and parameter description becomes "" and {"*args"}
 * respectively.
 */
class SpringRobotLibrary implements DynamicRobotLibrary {

    /** Spring application context. */
    private ApplicationContext context;

    /** Mapping of keyword name to spring bean name. */
    private Map<String, String> keywordToBeanMap;

    /**
     * Create new SpringRobotLibrary object using the given configuration.
     *
     * @param springConfigPath String configuration path
     */
    public SpringRobotLibrary(String springConfigPath) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfigPath);

        context.registerShutdownHook();
        this.context = context;
        this.keywordToBeanMap = KeywordUtils.getKeywordMap(context);
    }

    /**
     * Implements the Robot Framework interface method 'get_keyword_names' required for dynamic libraries.
     * Retrieve keyword names from the spring context by searching for classes implementing the
     * {@link org.jspringbot.Keyword} interface.
     *
     * @return String array containing all keyword names defined in the context.
     */
    public String[] getKeywordNames() {
        return keywordToBeanMap.keySet().toArray(new String[keywordToBeanMap.size()]);
    }

    /**
     * Implements the Robot Framework interface method 'run_keyword' required for dynamic libraries.
     *
     * @param keyword name of keyword to be executed
     * @param params parameters passed by Robot Framework
     * @return result of the keyword execution
     */
    public Object runKeyword(String keyword, final Object[] params) {
        try {
            return ((Keyword) context.getBean(keywordToBeanMap.get(keyword))).execute(params);
        } catch(Exception e) {
            e.printStackTrace(System.out);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Retrieves the documentation for the given keyword.
     *
     * @param keyword robot keyword
     * @return keyword description
     */
    public String getKeywordDocumentation(String keyword) {
        return KeywordUtils.getDescription(keyword, context, keywordToBeanMap);
    }

    /**
     * Retrieves parameter description for the given keyword.
     *
     * @param keyword robot keyword
     * @return parameter description
     */
    public String[] getKeywordArguments(String keyword) {
        return KeywordUtils.getParameters(keyword, context, keywordToBeanMap);
    }
}