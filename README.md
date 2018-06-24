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

Pre-requisite:
* GPG
   
1. https://central.sonatype.org/pages/working-with-pgp-signatures.html
2. Download "Installer for GnuPG"
3. Generate a Key Pair "gpg --gen-key"
    
    3.1. Leave passphrase blank
    3.2. Yes protection is not needed 
    3.3. List key >> "gpg2 --list-secret-keys"
    3.4. "gpg2 --keyserver keyserver.ubuntu.com --send-keys 42BBA46A8CB315CE987D093ECD594803614716ED"
  
4. Check "git config --list" to make sure github credentials are for jspringbot
   
[1] mvn release:clean

[2] mvn release:prepare

Type enter on the following questions:

What is the release version for "jspringbot"? (org.jspringbot:jspringbot) 1.6: : 

What is SCM release tag or label for "jspringbot"? (org.jspringbot:jspringbot) jspringbot-1.6: : 

What is the new development version for "jspringbot"? (org.jspringbot:jspringbot) 1.7-SNAPSHOT: : 

[3] mvn release:perform

[4] Change <version>1.7-SNAPSHOT</version> <version>1.6</version>

[5] mvn clean deploy

[6] Check 1.6 files is deployed in https://oss.sonatype.org/content/groups/public