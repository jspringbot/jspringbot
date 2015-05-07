package org.jspringbot.keyword.main;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jspringbot.JSpringBotLogger;

import java.util.ArrayList;
import java.util.List;

public class SoftAssertManager {

    private static final JSpringBotLogger LOGGER = JSpringBotLogger.getLogger(SoftAssertManager.class);

    public static final SoftAssertManager INSTANCE = new SoftAssertManager();

    private List<SoftAssertExceptionItem> captured = new ArrayList<SoftAssertExceptionItem>();

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void add(String keyword, Exception e) {
        captured.add(new SoftAssertExceptionItem(keyword, e));
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
        for(SoftAssertExceptionItem item : captured) {
            buf.append("\n   ").append("[").append(i++).append("]: ")
                    .append(item);
        }

        return buf;
    }
}
