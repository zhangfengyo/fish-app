@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0start_backend_safe.ps1" -Mode foreground
endlocal
