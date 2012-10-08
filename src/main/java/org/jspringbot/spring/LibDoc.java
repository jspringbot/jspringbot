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
