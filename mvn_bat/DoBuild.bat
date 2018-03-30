@echo off
cd ..
call mvn clean
timeout 1
call mvn compile
timeout 1
call mvn package
cd mvn_bat
pause  