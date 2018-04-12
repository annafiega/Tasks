call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo runcrud.bat has errors - breaking work
goto fail

:runbrowser

start "start" "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo Website cannot be open
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.