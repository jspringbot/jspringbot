package org.jspringbot.keyword.main;

import org.jspringbot.lifecycle.LifeCycleAdapter;

import java.util.Map;

public class SoftAssertLifeCycleHandler extends LifeCycleAdapter {

    @Override
    public void endTest(String name, Map attributes) {
        SoftAssertManager.INSTANCE.setEnable(false);
        SoftAssertManager.INSTANCE.clear();
    }
}
