@echo off
color 0B
echo ===================================================
echo               FovChanger Auto-Uploader
echo ===================================================
echo.

if not exist ".git" (
    color 0C
    echo [ERROR] Git repository not found!
    pause
    exit /b
)

echo [1/4] Syncing with GitHub (Safe Pull)...
:: Забираем изменения с сайта. Если конфликтов нет - все пройдет гладко.
git pull origin main --no-rebase

echo [2/4] Adding all files...
git add .

echo [3/4] Creating commit...
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set datetime=%%I
set timestamp=%datetime:~0,4%-%datetime:~4,2%-%datetime:~6,2% %datetime:~8,2%:%datetime:~10,2%

git commit -m "Auto-update: %timestamp%"

echo [4/4] Pushing to GitHub...
git push origin main

if %ERRORLEVEL% neq 0 (
    color 0C
    echo.
    echo [ERROR] Push failed! Check the text above.
) else (
    color 0A
    echo.
    echo ===================================================
    echo UPLOAD SUCCESSFUL!
    echo ===================================================
)

echo.
pause