package org.jspringbot.keyword.main;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

@Component
@KeywordInfo(
        name = "Start Soft Assert",
        description = "classpath:desc/StartSoftAssert.txt"
)
public class StartSoftAssert implements Keyword {

    @Override
    public Object execute(Object[] params) throws Exception {
        SoftAssertManager.INSTANCE.setEnable(true);

        return null;
    }
}
