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

