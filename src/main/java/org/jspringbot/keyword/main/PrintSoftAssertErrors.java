package org.jspringbot.keyword.main;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

@Component
@KeywordInfo(
        name = "Print Soft Assert Errors",
        description = "classpath:desc/PrintSoftAssertErrors.txt"
)
public class PrintSoftAssertErrors implements Keyword {
    @Override
    public Object execute(Object[] params) throws Exception {
        SoftAssertManager.INSTANCE.print();

        return null;
    }
}
