# README_AGENT_FOR_CLIENT

## Purpose

This file is written for an AI agent or technical operator who needs to start, verify, and troubleshoot this project for a customer.

Project root:

`D:\earnm\debug\fish-app`

This is a portable Windows project. Keep the entire folder together, including `appdata`.

## Core Rules

1. Do not move or rename `appdata`.
2. Do not rewrite uploaded media paths to absolute machine paths.
3. Do not move images, videos, or attachments into cache or temp folders.
4. Prefer the provided startup scripts over custom Java or Maven commands.
5. Unless the user explicitly says otherwise, do not close port `3000`.

## What This Project Needs

The target machine should have:

1. Windows
2. JDK 8 or newer
3. MySQL server
4. MySQL client command available as `mysql`
5. PowerShell

Quick checks:

```powershell
java -version
mysql --version
```

## Default Runtime Settings

Unless the customer gives different settings, assume:

- HTTP port: `8080`
- Context path: `/guanshangl0g62fht`
- MySQL host: `127.0.0.1`
- MySQL port: `3306`
- MySQL database: `guanshangl0g62fht`
- MySQL username: `root`
- MySQL password: `971021`

## If The Customer Uses Different MySQL Settings

Set these environment variables before initialization or startup:

```powershell
$env:APP_DB_URL="jdbc:mysql://127.0.0.1:3306/guanshangl0g62fht?useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true"
$env:APP_DB_USERNAME="root"
$env:APP_DB_PASSWORD="your_mysql_password"
$env:SERVER_PORT="8080"
$env:SERVER_CONTEXT_PATH="/guanshangl0g62fht"
$env:APP_HOME="D:\earnm\debug\fish-app"
$env:APP_FILES_DIR="D:\earnm\debug\fish-app\appdata"
```

Optional mail settings:

```powershell
$env:APP_MAIL_USERNAME="your_mail_account"
$env:APP_MAIL_PASSWORD="your_mail_password"
```

## First-Time Setup

If the database is missing, empty, or the customer wants a clean rebuild, run:

```bat
init_database_safe.bat
```

Equivalent PowerShell:

```powershell
.\init_database_safe.ps1
```

This imports the repaired SQL files from the local `db` directory and rebuilds the working data set.

## Standard Startup

Preferred background start:

```bat
start_backend_safe.bat
```

Equivalent PowerShell:

```powershell
.\start_backend_safe.ps1
```

Foreground start, only if the customer wants live logs:

```bat
start_backend_foreground.bat
```

Equivalent PowerShell:

```powershell
.\start_backend_safe.ps1 -Mode foreground
```

## Stop

Preferred stop command:

```bat
stop_backend_safe.bat
```

Equivalent PowerShell:

```powershell
.\start_backend_safe.ps1 -Mode stop
```

## Status Check

Use:

```powershell
.\start_backend_safe.ps1 -Mode status
```

Expected results:

- `RUNNING`
- `STOPPED`

## URLs To Verify

Frontend:

`http://127.0.0.1:8080/guanshangl0g62fht/front/index.html`

Admin:

`http://127.0.0.1:8080/guanshangl0g62fht/admin/index.html`

Health endpoint:

```powershell
Invoke-WebRequest -UseBasicParsing "http://127.0.0.1:8080/guanshangl0g62fht/news/list?page=1&limit=1"
```

If startup is successful, this should return HTTP `200`.

## Demo Accounts

Admin:

- username: `admin`
- password: `admin`

Frontend user:

- username: `12`
- password: `123456`

## What The Agent Should Report After Success

Use a concise format like this:

```text
Project started successfully.
Frontend: http://127.0.0.1:8080/guanshangl0g62fht/front/index.html
Admin: http://127.0.0.1:8080/guanshangl0g62fht/admin/index.html
User account: 12 / 123456
Admin account: admin / admin
Stop command: stop_backend_safe.bat
```

## Common Bug Handling

### 1. Java command not found

Symptoms:

- `java` is not recognized
- startup script fails before backend starts

Fix:

1. Install JDK 8 or newer
2. Set `JAVA_HOME`, or add `java` to `PATH`
3. Re-run:

```powershell
java -version
```

### 2. MySQL command not found

Symptoms:

- `mysql` is not recognized
- database init script fails immediately

Fix:

1. Install MySQL client tools
2. Add `mysql` to `PATH`
3. Re-run:

```powershell
mysql --version
```

### 3. MySQL login failure

Symptoms:

- access denied
- init script or startup cannot connect to database

Fix:

1. Confirm MySQL service is running
2. Confirm username and password
3. Set:

```powershell
$env:APP_DB_USERNAME="root"
$env:APP_DB_PASSWORD="your_mysql_password"
```

4. Re-run database initialization

### 4. Table does not exist

Symptoms:

- errors like `table ... does not exist`
- login API or content API fails

Fix:

1. Run:

```bat
init_database_safe.bat
```

2. Wait for SQL import to finish
3. Start backend again

### 5. Frontend or admin page is blank

Symptoms:

- page opens but shows blank content
- browser loads old page after an update

Fix:

1. Hard refresh the browser with `Ctrl+F5`
2. Re-open:

`http://127.0.0.1:8080/guanshangl0g62fht/front/index.html`

3. If still blank, restart backend with:

```bat
stop_backend_safe.bat
start_backend_safe.bat
```

Notes:

- This project already disables caching for front and admin entry resources
- In most cases, a hard refresh or backend restart is enough

### 6. Images or videos do not display

Symptoms:

- product images missing
- encyclopedia images missing
- uploaded files return 404

Fix:

1. Confirm `appdata\upload` still exists
2. Confirm the whole project folder was moved together with `appdata`
3. Do not replace relative paths with absolute machine paths
4. Restart backend

### 7. Smart assistant does not work

Symptoms:

- assistant cannot answer
- assistant API call fails

Fix:

1. Open admin page
2. Enter the assistant API URL, API key, and model in admin settings
3. Use the model-fetch function in admin if needed
4. Save settings
5. Test assistant again

Important:

- The assistant should use admin-configured URL, API key, and model
- Do not rely on cache as the configuration source

### 8. Port 8080 is already occupied

Symptoms:

- backend cannot start
- startup script reports port conflict

Fix:

1. Check:

```powershell
Get-NetTCPConnection -LocalPort 8080 -State Listen
```

2. Stop the conflicting process, or change:

```powershell
$env:SERVER_PORT="8081"
```

3. Start backend again

If the port changes, the frontend and admin URLs must use the new port.

## Recommended Debug Order

If the customer says "it does not work", debug in this exact order:

1. Check `java -version`
2. Check `mysql --version`
3. Confirm MySQL service is running
4. Re-run `init_database_safe.bat` if DB looks broken
5. Re-run `start_backend_safe.bat`
6. Verify health endpoint
7. Open frontend URL
8. Open admin URL
9. If still failing, inspect logs

Useful checks:

```powershell
Get-NetTCPConnection -LocalPort 8080 -State Listen
```

```powershell
Get-Content .\target\backend.out.log -Tail 50
Get-Content .\target\backend.err.log -Tail 50
```

## Important Portability Notes

1. This project is intended to run from its own folder.
2. `appdata` is required for media files and generated outputs.
3. Relative media paths are intentional and should be preserved.
4. Do not rewrite file reads to old Chinese directories or machine-specific paths.
5. The startup scripts already rebuild the jar when source files change.

## Preferred Agent Behavior

When helping a customer:

1. Prefer the provided scripts.
2. Prefer minimal changes.
3. Reproduce the issue before changing anything.
4. If the database looks damaged, rebuild it with the safe init script.
5. After each fix, verify frontend, admin, and health endpoint.
