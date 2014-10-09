#! /bin/bash

MESOS_MASTER=$1

mvn clean package

exec java -Djava.library.path=/usr/local/lib \
  -jar target/mesos-compat-0.1.0-SNAPSHOT-jar-with-dependencies.jar \
  $MESOS_MASTER
