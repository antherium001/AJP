@echo off
setlocal

set SERVLET_API=C:\apache-tomcat-10.1.57\lib\servlet-api.jar
set MYSQL_JAR=C:\Users\Admin\eclipse-workspace\AJP\mysql-connector-j-9.7.0.jar
set SRC_DIR=C:\Users\Admin\eclipse-workspace\AJP\src
set CLASSES_DIR=C:\Users\Admin\eclipse-workspace\AJP\build\AJP\WEB-INF\classes
set WEB_DIR=C:\Users\Admin\eclipse-workspace\AJP\build\AJP

echo Compiling with Java 21...

REM Clean old classes
if exist "%CLASSES_DIR%" rd /s /q "%CLASSES_DIR%"
mkdir "%CLASSES_DIR%"

REM Compile all servlet classes targeting Java 21
for /r "%SRC_DIR%" %%f in (*.java) do (
    javac --release 21 -cp "%SERVLET_API%;%MYSQL_JAR%" -d "%CLASSES_DIR%" -sourcepath "%SRC_DIR%" "%%f" 2>&1
)

REM Copy HTML files from WebContent
copy /y "C:\Users\Admin\eclipse-workspace\AJP\WebContent\*.html" "%WEB_DIR%\" >nul 2>&1

REM Copy web.xml and lib
if not exist "%WEB_DIR%\WEB-INF\lib" mkdir "%WEB_DIR%\WEB-INF\lib"
copy /y "C:\Users\Admin\eclipse-workspace\AJP\WebContent\WEB-INF\web.xml" "%WEB_DIR%\WEB-INF\" >nul
copy /y "C:\Users\Admin\eclipse-workspace\AJP\mysql-connector-j-9.7.0.jar" "%WEB_DIR%\WEB-INF\lib\" >nul

echo Done. All classes compiled targeting Java 21.
endlocal
