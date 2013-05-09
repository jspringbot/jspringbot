package org.jspringbot;

import java.util.Map;

public interface Visitor<T> {

    void visit(T type);
}
