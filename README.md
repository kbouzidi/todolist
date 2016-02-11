# Todo List Web App

[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org) [![Build Status](https://api.travis-ci.org/kbouzidi/todolist.svg?branch=master)](https://travis-ci.org/kbouzidi/todolist/branches)  [![Build Status](https://codeship.com/projects/061c9970-ae2b-0133-3079-7e50fc25e7b7/status?branch=master)](https://codeship.com/projects/132245)


## Overall directory structure
```
todolist/
  |- src/
  |- .gitignore
  |- .travis.yml
  |- pom.xml
  |- LICENSE
  |- README.md
  |- run.sh
```

## Description 
TO-DO list to track project progress.

A web App for easily organising project, tasks. Check out the live demo.

Todo comes with a broad range of features including nested adding new Task, new User, new project, with clean API and modern views. 
It's best viewed in a modern browser but fallbacks will be available soon.


## API description
 
 - [Todo List API](http://docs.todolist21.apiary.io) 

## Environment setup
Download and setup the following applications:

- [JAVA JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
- [Spark](http://sparkjava.com) 
- [Maven](https://maven.apache.org/)
- [Postgres](http://www.postgresql.org/)
- [Angular Material](https://material.angularjs.org/latest/)

For Windows users please install `Cygwin`

- [Cygwin](https://www.cygwin.com/)


## Application server
 - `Jetty v9.3.0.M2`

## Version control
 - `Github`

## Server installation 
Go to the server folder and execute the following commands

### Database
- `psql -c "CREATE USER sylvain WITH PASSWORD 'sylvain';"`
- `psql -c 'create database tests;' -U postgres`
- `psql -c 'grant all privileges on database tests to sylvain;' -U postgres`

## Maven
- `mvn clean install`

## Run Batch
 - `mvn jetty:run`


### Contributing

TODO welcomes any and all contributions! Please read the [CONTRIBUTING guide](https://github.com/kbouzidi/todolist/blob/master/CONTRIBUTING.md) for details on developing and submitting your contributions.