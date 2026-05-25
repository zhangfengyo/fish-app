# AGENTS.md

## Purpose

This file tells an AI agent exactly how to start, initialize, verify, and stop this project on a Windows machine.

Project root:

`D:\earnm\debug\fish-app`

This project is a repaired portable copy. It is intended to run from its own folder together with its local `appdata` directory.

## What The Agent Should Do

When the user asks to start this project, follow this order:

1. Check prerequisites.
2. Initialize the database if needed.
3. Start the backend with the provided safe script.
4. Verify the frontend URL opens.
5. Report the correct URLs and demo accounts.

Do not invent a new startup flow if the provided scripts already work.

## Environment Requirements

The target machine should have:

1. Windows.
2. JDK 8 or newer.
3. MySQL server and MySQL client.
4. PowerShell available.

Preferred checks:

```powershell
java -version
mysql --version
```

If `JAVA_HOME` is not set, `java` must still be available on `PATH`.

If `MYSQL_HOME` is not set, `mysql.exe` must still be available on `PATH`.

## Default Runtime Assumptions

Unless the user says otherwise, assume:

- HTTP port: `8080`
- Context path: `/guanshangl0g62fht`
- MySQL host: `127.0.0.1`
- MySQL port: `3306`
- MySQL database: `guanshangl0g62fht`
- MySQL username: `root`
- MySQL password: `971021`

## Optional Environment Variables

If the target machine uses different settings, set these before initialization or startup:

```powershell
$env:APP_DB_URL="jdbc:mysql://127.0.0.1:3306/guanshangl0g62fht?useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true"
$env:APP_DB_USERNAME="root"
$env:APP_DB_PASSWORD="your_mysql_password"
$env:SERVER_PORT="8080"
$env:SERVER_CONTEXT_PATH="/guanshangl0g62fht"
$env:APP_HOME="D:\earnm\debug\fish-app"
$env:APP_FILES_DIR="D:\earnm\debug\fish-app\appdata"
```

Optional mail variables:

```powershell
$env:APP_MAIL_USERNAME="your_mail_account"
$env:APP_MAIL_PASSWORD="your_mail_password"
```

## Database Initialization

If the database is missing, empty, or the user asks for a fresh setup, run:

```bat
init_database_safe.bat
```

Equivalent PowerShell:

```powershell
.\init_database_safe.ps1
```

This script imports the repaired SQL files from the local `db` directory and recreates the working data set.

The agent should prefer the provided script over manual `mysql < file.sql` steps.

## Start Commands

### Recommended background start

```bat
start_backend_safe.bat
```

Equivalent PowerShell:

```powershell
.\start_backend_safe.ps1
```

### Foreground start

Use this only if the user explicitly wants live logs in the terminal:

```bat
start_backend_foreground.bat
```

Equivalent PowerShell:

```powershell
.\start_backend_safe.ps1 -Mode foreground
```

## Stop Commands

Preferred:

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

Expected outputs:

- `RUNNING`
- `STOPPED`

## Health Verification

After startup, verify at least one of these:

```powershell
Invoke-WebRequest -UseBasicParsing "http://127.0.0.1:8080/guanshangl0g62fht/news/list?page=1&limit=1"
```

Or open the frontend:

Frontend:

`http://127.0.0.1:8080/guanshangl0g62fht/front/index.html`

Admin:

`http://127.0.0.1:8080/guanshangl0g62fht/admin/index.html`

## Demo Accounts

Admin:

- username: `admin`
- password: `admin`

Frontend user:

- username: `12`
- password: `123456`

## Files And Portability Rules

The agent should preserve these rules:

1. Keep the whole project folder together with `appdata`.
2. Do not rewrite media paths to absolute machine paths.
3. Do not move uploaded files into temp or cache directories.
4. Use the local project `appdata\upload` and `appdata\output` directories.
5. Port `3000` is reserved and must never be closed, stopped, or killed unless the user explicitly asks to close port `3000` in the current turn.

## Important Script Behavior

`start_backend_safe.ps1` already does the following:

1. Uses the local project folder as `APP_HOME` if not overridden.
2. Uses local `appdata` as `APP_FILES_DIR` if not overridden.
3. Builds the jar with `mvnw.cmd -q -DskipTests package` if needed.
4. Starts the backend jar in background or foreground depending on mode.
5. Checks health automatically.

Because of that, the agent should not replace it with a custom Maven or Java command unless the script fails.

## If Startup Fails

The agent should debug in this order:

1. Confirm `java` is available.
2. Confirm `mysql` is available.
3. Confirm MySQL credentials are correct.
4. Re-run database initialization.
5. Re-run background startup.
6. If needed, run foreground startup and inspect the error.

Useful checks:

```powershell
Get-NetTCPConnection -LocalPort 8080 -State Listen
```

```powershell
Get-Content .\target\backend.out.log -Tail 50
Get-Content .\target\backend.err.log -Tail 50
```

## What The Agent Should Tell The User After Success

Report:

1. Backend is running.
2. Frontend URL.
3. Admin URL.
4. Demo accounts.
5. How to stop the project.

Suggested concise success format:

```text
Project started successfully.
Frontend: http://127.0.0.1:8080/guanshangl0g62fht/front/index.html
Admin: http://127.0.0.1:8080/guanshangl0g62fht/admin/index.html
User account: 12 / 123456
Admin account: admin / admin
Stop command: stop_backend_safe.bat
```
