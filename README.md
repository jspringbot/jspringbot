jSpringBot [![Build Status](https://travis-ci.org/jspringbot/jspringbot-selenium.svg?branch=master)](https://travis-ci.org/jspringbot/jspringbot-selenium)
=======

### Introduction

For end-to-end acceptance testing using Robot Framework with Java, Spring and Maven.

For details please see: http://jspringbot.org/

### Robotframework, GitHub

Built for [Robotframework](https://code.google.com/p/robotframework/), and is managed through GitHub to help open source community.

### Java, Maven, Spring

All libraries were built using [Java](http://www.java.com/en/) and [Spring Framework](http://www.springsource.org/spring-framework). Dependencies and test execution are done through [Maven](http://maven.apache.org/).

### Copyright and license

Copyright 2012 JSpringBot

Code licensed under [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).

### Releasing jSpringBot

#### Releasing Pre-requisite

1. GPG 
   
    https://central.sonatype.org/pages/working-with-pgp-signatures.html
    
    A. Download "Installer for GnuPG"    
    B. Generate a Key Pair "gpg --gen-key"    
        B.1. Leave passphrase blank    
        B.2. Yes protection is not needed   
        B.3. List key >> <code>"gpg2 --list-secret-keys"</code>  
        B.4. <code>"gpg2 --keyserver keyserver.ubuntu.com --send-keys 42BBA46A8CB315CE987D093ECD594803614716ED"</code>  
    C. Check "git config --list" to make sure github credentials are for jspringbot
   
2. Pom Setup

    https://central.sonatype.org/pages/apache-maven.html

3. Settings.xml

    https://central.sonatype.org/pages/apache-maven.html

4. Git User to use for releasing to Nexus 

#### Releasing Sequence

[1] jspringbot

[2] jspringbot-maven-plugin (update jspringbot to latest release version) DO NEXT

[3] jspringbot-libraries (update jspringbot to latest release version)

[4] jspringbot-seleniu, jspringbot-http, jspringbot-xml, etc (update jspringbot to latest release version)

[5] jsprintbot-base (update jspringbot and libraries to latest release version)  

#### Releasing A Project

[1] mvn release:clean

[2] mvn release:prepare

    Type enter on the following questions:
    What is the release version for "jspringbot"? (org.jspringbot:jspringbot) 1.6: : 
    What is SCM release tag or label for "jspringbot"? (org.jspringbot:jspringbot) jspringbot-1.6: : 
    What is the new development version for "jspringbot"? (org.jspringbot:jspringbot) 1.7-SNAPSHOT: : 

[3] mvn release:perform

* Verify release version in github ie. https://github.com/jspringbot/jspringbot/releases

[4] Change <version>1.7-SNAPSHOT</version> <version>1.6</version>

[5] mvn clean deploy

* Check release version of file is deployed in https://oss.sonatype.org/content/groups/public