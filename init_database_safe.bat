@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0init_database_safe.ps1"
endlocal
