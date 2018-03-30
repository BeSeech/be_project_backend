@echo off
cd ..
call mvn dependency:resolve
cd mvn_bat
pause