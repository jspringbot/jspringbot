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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;

import java.io.IOException;

public class LibDoc {
    /** Setting library scope to GLOBAL. */
    public static final String ROBOT_LIBRARY_SCOPE = "TEST SUITE";

    private TestSuiteSpringLibrary library;

    public LibDoc() {
        library = new TestSuiteSpringLibrary("spring-libdoc.xml");
    }

    public String[] getKeywordNames() {
        return library.getKeywordNames();
    }

    public Object runKeyword(String name, Object[] arguments) {
        return null;
    }

    public String[] getKeywordArguments(String name) {
        return library.getKeywordArguments(name);
    }

    public String getKeywordDocumentation(String name) {
        if(name.equals("__intro__")) {
            ResourceEditor editor = new ResourceEditor();
            editor.setAsText("libdoc.intro");
            Resource r = (Resource) editor.getValue();

            try {
                return IOUtils.toString(r.getInputStream());
            } catch (IOException ignore) {
                return "No introduction provided.";
            }
        }

        return library.getKeywordDocumentation(name);
    }

}
