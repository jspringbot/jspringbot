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

import java.lang.annotation.*;

/**
 * Annotation for describing robot keyword function and usage.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeywordInfo {

    /**
     * Keyword name.
     * @return keyword name
     */
    String name();

    /**
     * Keyword description.
     * @return keyword description
     */
    String description() default "";

    /**
     * Keyword parameter description.
     * <h4>How to represent arguments</h4>
     * <table style="border:1px solid #CCC; border-collapse: collapse;">
     *     <tr>
     *         <th style="border: 1px solid #CCC; padding: 4px;">
     *             Expected arguments
     *         </th>
     *         <th style="border: 1px solid #CCC; padding: 4px;">
     *             How to represent
     *         </th>
     *         <th style="border: 1px solid #CCC; padding: 4px;">
     *             Examples
     *         </th>
     *     </tr>
     *     <tr>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             No arguments
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             Empty list/array
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             new String[0]
     *         </td>
     *     </tr>
     *     <tr>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             One or more argument
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             List/array of strings containing argument names.
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             {"one_argument"}
     *             <br/>
     *             {"arg1", "arg2", "arg3"}
     *         </td>
     *     </tr>
     *     <tr>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             Default values for arguments
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             Default values separated from names with '='.
     *             <br/>
     *             Defaults are always considered to be String.
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             {"arg=default value"}
     *             <br/>
     *             {"a", "b=1", "c=2"}
     *         </td>
     *     </tr>
     *     <tr>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             Variable number of arguments
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             Last argument has '*' before its name.
     *         </td>
     *         <td style="border: 1px solid #CCC; padding: 4px; margin-left: 5px;">
     *             {"*arguments"}
     *             <span style="color:blue;">
     *                 (0 or more) This is the default.
     *             </span>
     *             <br/>
     *             {"a", "b=42", "*rest"}
     *         </td>
     *     </tr>
     * </table>
     * @return keyword parameter description
     */
    String[] parameters() default {};
}

