@echo off
cd ..
call mvn clean
timeout 3 
call mvn surefire-report:report
cd mvn_bat
pause  