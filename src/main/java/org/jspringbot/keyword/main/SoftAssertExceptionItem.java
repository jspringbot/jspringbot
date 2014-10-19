package org.jspringbot.keyword.main;

public class SoftAssertExceptionItem {

    private Exception e;
    private String keyword;

    public SoftAssertExceptionItem(String keyword, Exception e) {
        this.keyword = keyword;
        this.e = e;
    }

    public Exception getE() {
        return e;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return "(" + keyword + ") -> " + e.getMessage();
    }
}
