package org.jspringbot.keyword.main;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

@Component
@KeywordInfo(
        name = "Flush Soft Assert Errors",
        description = "classpath:desc/FlushSoftAssertErrors.txt"
)
public class FlushSoftAssertErrors implements Keyword {
    @Override
    public Object execute(Object[] params) throws Exception {
        SoftAssertManager.INSTANCE.flush();

        return null;
    }
}
