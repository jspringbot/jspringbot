package org.jspringbot.keyword.main;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SoftAssertManager {

    public static final SoftAssertManager INSTANCE = new SoftAssertManager();

    private List<Exception> captured = new ArrayList<Exception>();

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void add(Exception e) {
        captured.add(e);
    }

    public void clear() {
        captured.clear();
    }

    public boolean hasErrors() {
        return CollectionUtils.isNotEmpty(captured);
    }

    public void flush() {
        if(!hasErrors()) {
            return;
        }

        StringBuilder buf = getErrors();

        // ensure that this is thrown properly
        setEnable(false);
        throw new IllegalStateException(buf.toString());
    }

    public StringBuilder getErrors() {
        StringBuilder buf = new StringBuilder("Soft Assert Errors: ");

        int i = 1;
        for(Exception e : captured) {
            buf.append("\n   ").append("[").append(i++).append("]: ").append(e.getMessage());
        }

        return buf;
    }
}
