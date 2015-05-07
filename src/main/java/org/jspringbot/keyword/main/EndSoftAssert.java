package org.jspringbot.keyword.main;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

@Component
@KeywordInfo(
        name = "End Soft Assert",
        description = "classpath:desc/EndSoftAssert.txt"
)
public class EndSoftAssert implements Keyword {

    @Override
    public Object execute(Object[] params) throws Exception {
        SoftAssertManager.INSTANCE.setEnable(false);

        return null;
    }
}
