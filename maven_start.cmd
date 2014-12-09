@ECHO OFF
REM.-- Prepare the Command Processor
SETLOCAL ENABLEEXTENSIONS
SETLOCAL ENABLEDELAYEDEXPANSION
set MAVEN_OPTS=-Dfile.encoding=UTF-8 -Duser.timezone=GMT+8
rem set MVN=%MAVEN_HOME%\bin\mvn
set MVN=mvn
echo %MAVEN_OPTS%

:menuLOOP
echo.
echo.= Menu =================================================
echo.
for /f "tokens=1,2,* delims=_ " %%A in ('"findstr /b /c:":menu_" "%~f0""') do echo.  %%B  %%C
set choice=
echo.&set /p choice=Make a choice or hit ENTER to quit: ||GOTO:EOF
echo.&call:menu_%choice%
GOTO:menuLOOP

::-----------------------------------------------------------
:: menu functions follow below here
::-----------------------------------------------------------

:menu_1   清理maven目录
call %MVN% clean
GOTO:EOF

:menu_2   重建eclipse工程
call %MVN% eclipse:clean eclipse:eclipse -U -DskipTests
GOTO:EOF

:menu_4   构建war包
call %MVN% clean package -U -DskipTests
move target\*.war
GOTO:EOF

:menu_

:menu_T   Tip
echo.It's easy to add a line separator using one or more fake labels
GOTO:EOF

:menu_C   Clear Screen
cls
GOTO:EOF

