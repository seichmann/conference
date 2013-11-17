Conference Application
==========

This application can be used to manage conferences, talks, speakers and rooms. 

Users which want to participate on some conference can have a detailed tree view and schedule for each conference.

System requirements
=================

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7.

A running mysql-database with following settings is required:

<pre>
user: conference
password: conference
schema: conference
</pre>

Build and Deploy
================

There are three different maven profiles for different purposes.

Creates ear-file for manual deployment:

<pre>mvn clean package</pre>

Automatically deploys ear file to running jboss instance for development:

<pre>mvn clean install -Pdev</pre>

Install ear file in local repository when all unit tests passes:

<pre>mvn clean install -Parq-jbossas-remote</pre>

Access application
==================

UI can be accessed via web browser under following url:

<pre>http://localhost:8080/conference-web/</pre>
