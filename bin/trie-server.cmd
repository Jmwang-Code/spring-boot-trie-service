@echo off
if "%1"=="start" goto START
:START
cd /d %~dp0
cd ..
"C:\Users\jmw\.jdks\corretto-17.0.6\bin\java" -server -Xms2G -Xmx2G -XX:+UseG1GC -Dspring.profiles.active=config -Dfile.encoding=UTF-8 -cp ".\lib\*" com.cn.jmw.RegressionSearchTree