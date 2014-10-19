package org.jspringbot.keyword.main;

import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

@Component
@KeywordInfo(
        name = "Soft Assert",
        parameters = {"value"},
        description = "classpath:desc/SoftAssert.txt"
)
public class SoftAssert implements Keyword {

    @Override
    public Object execute(Object[] params) throws Exception {
        boolean value =  Boolean.parseBoolean(String.valueOf(params[0]));

        SoftAssertManager.INSTANCE.setEnable(value);

        return null;
    }
}
