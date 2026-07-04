#!/usr/bin/env sh
##############################################################################
# Gradle start up script for UN*X
##############################################################################
if [ -z "$CLASSPATH" ]; then
  CLASSPATH=""
fi
DIRNAME=`dirname "$0"`
APP_BASE_NAME=`basename "$0"`
CLASSPATH="$DIRNAME/gradle/wrapper/gradle-wrapper.jar:$CLASSPATH"
if [ -z "$JAVA_HOME" ]; then
  JAVA_CMD=java
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi
exec "$JAVA_CMD" -Dorg.gradle.appname="$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
