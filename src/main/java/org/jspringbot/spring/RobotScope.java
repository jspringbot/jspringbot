package org.jspringbot.spring;

import org.apache.commons.lang.StringUtils;

public enum RobotScope {
    ALL("ALL"),
    GLOBAL("GLOBAL"),
    SUITE("TEST SUITE"),
    TEST("TEST CASE");

    String value;

    private RobotScope(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RobotScope toRobotScope(String value) {
        for (RobotScope scope : values()) {
            if(StringUtils.equals(scope.getValue(), value)) {
                return scope;
            }
        }

        throw new IllegalArgumentException(String.format("Unknown robot scope '%s'.", value));
    }
}
