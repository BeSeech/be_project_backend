@echo off
cd ..
call mvn clean
timeout 1
call mvn compile
timeout 1
call mvn package
timeout 1
call mvn cargo:deploy -X
cd mvn_bat
pause  