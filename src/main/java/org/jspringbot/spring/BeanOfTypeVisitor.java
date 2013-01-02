package org.jspringbot.spring;

import java.util.Map;

public interface BeanOfTypeVisitor<T> {

    void visit(Map<String, T> beans);
}
