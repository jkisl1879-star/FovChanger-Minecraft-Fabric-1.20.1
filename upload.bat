@echo off
color 0B
echo ===================================================
echo               FovChanger Auto-Uploader
echo ===================================================
echo.

:: Проверяем, инициализирован ли Git в этой папке
if not exist ".git" (
    color 0C
    echo [ERROR] Git repository not found in this folder!
    echo Please run "git init" first.
    pause
    exit /b
)

echo [1/3] Adding all files...
git add .

echo [2/3] Creating commit...
:: Создаем коммит с текущей датой и временем
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set datetime=%%I
set timestamp=%datetime:~0,4%-%datetime:~4,2%-%datetime:~6,2% %datetime:~8,2%:%datetime:~10,2%

git commit -m "Auto-update: %timestamp%"

echo [3/3] Pushing to GitHub...
git push

if %ERRORLEVEL% neq 0 (
    color 0C
    echo.
    echo [ERROR] Push failed! Check if you have internet or if there are conflicts.
) else (
    color 0A
    echo.
    echo ===================================================
    echo UPLOAD SUCCESSFUL!
    echo ===================================================
)

echo.
pause