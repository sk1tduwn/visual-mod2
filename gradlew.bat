@echo off
SET DIRNAME=%~dp0
SET CLASSPATH=%DIRNAME%gradle\wrapper\gradle-wrapper.jar;%CLASSPATH%
IF DEFINED JAVA_HOME (
  SET JAVA_CMD=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVA_CMD=java
)
"%JAVA_CMD%" -Dorg.gradle.appname=%~n0 -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
