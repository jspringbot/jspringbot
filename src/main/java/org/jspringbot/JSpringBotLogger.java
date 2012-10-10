/*
 * Copyright (c) 2012. JSpringBot Shiela D. Buitizon. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The JSpringBot licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jspringbot;

public class JSpringBotLogger {

    public static JSpringBotLogger getLogger(Class logger) {
        return new JSpringBotLogger(logger);
    }

    protected Class logger;

    protected JSpringBotLogger(Class logger) {
        this.logger = logger;
    }

    public void info(String msg, Object... args) {
        log("INFO", msg, args);
    }

    public void warn(String msg, Object... args) {
        log("WARN", msg, args);
    }

    public void trace(String msg, Object... args) {
        log("TRACE", msg, args);
    }

    public void debug(String msg, Object... args) {
        log("DEBUG", msg, args);
    }

    public void html(String msg, Object... args) {
        log("HTML", msg, args);
    }

    public void pureHtml(String msg, Object... args) {
        if(args != null && args.length > 0) {
            System.out.println(String.format("*HTML* %s", String.format(msg, args)));
        } else {
            System.out.println(String.format("*HTML* %s", msg));
        }
    }

    public void log(String level, String msg, Object... args) {
        if(args != null && args.length > 0) {
            System.out.println(String.format("*%s* [%s] %s", level, logger.getSimpleName(), String.format(msg, args)));
        } else {
            System.out.println(String.format("*%s* [%s] %s", level, logger.getSimpleName(), msg));
        }
    }
}
