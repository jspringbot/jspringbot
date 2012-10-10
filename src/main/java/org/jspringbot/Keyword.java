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
 * Interface that all keywords should implement.
 * {@link org.jspringbot.spring.SpringRobotLibrary} determines implemented keywords by scanning
 * the spring context for beans implementing this interface.
 */
public interface Keyword {

    /**
     * Executes the keyword.
     *
     * @param params keyword arguments; Robot Framework passes empty array (not null) when no arguments are passed.
     * @return result of the keyword execution.
     */
    Object execute(Object[] params) throws Exception;
}