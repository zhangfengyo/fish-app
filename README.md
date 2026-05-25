# fish-app

Portable repaired copy of the project:

`D:\earnm\debug\fish-app`

## What was fixed

- Removed the dependency on the original Chinese path that broke `mvnw.cmd`.
- Replaced machine-specific startup assumptions with env-driven startup.
- Moved writable file storage out of the packaged classpath.
- Fixed front/admin static routing so the pages load from stable URLs.
- Added a clean bootstrap database script because the original dump files were corrupted.
- Added portable content import scripts so real articles, encyclopedia entries, shop data, forum posts, aquarium data, and reminders can be rebuilt on another computer.
- Locked runtime media reads to the project-local `appdata` directory to avoid absolute-path dependencies.

## Requirements

1. Install JDK 8 or newer.
2. Set `JAVA_HOME`, or make sure `java` is on `PATH`.
3. Install MySQL and make sure `mysql` is on `PATH`, or set `MYSQL_HOME`.
4. MySQL should either use the default credentials `root` / `971021`, or you should override them with env vars before initializing or starting.

## Database defaults

The portable scripts default to:

- Host: `127.0.0.1`
- Port: `3306`
- Database: `guanshangl0g62fht`
- Username: `root`
- Password: `971021`

If another computer uses different MySQL credentials, override them with env vars.

## Optional DB env vars

PowerShell example:

```powershell
$env:APP_DB_URL="jdbc:mysql://127.0.0.1:3306/guanshangl0g62fht?useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true"
$env:APP_DB_USERNAME="root"
$env:APP_DB_PASSWORD="your_mysql_password"
```

Optional mail settings:

```powershell
$env:APP_MAIL_USERNAME="your_mail_account"
$env:APP_MAIL_PASSWORD="your_mail_password"
```

Optional runtime settings:

```powershell
$env:SERVER_PORT="8080"
$env:SERVER_CONTEXT_PATH="/guanshangl0g62fht"
$env:APP_HOME="D:\apps\fish-app"
$env:APP_FILES_DIR="D:\apps\fish-app\appdata"
```

## Initialize the database

Recommended:

```bat
init_database_safe.bat
```

This imports [minimal_bootstrap.sql](D:/earnm/debug/fish-app/db/minimal_bootstrap.sql), which recreates the `guanshangl0g62fht` database with the full table set and demo data needed for local startup.

It then automatically imports the repaired content scripts:

- [real_content_data.sql](D:/earnm/debug/fish-app/db/real_content_data.sql)
- [news_portable.sql](D:/earnm/debug/fish-app/db/news_portable.sql)
- [fix_forum.sql](D:/earnm/debug/fish-app/db/fix_forum.sql)
- [remaining_data.sql](D:/earnm/debug/fish-app/db/remaining_data.sql)
- [siyangjiaoxue.sql](D:/earnm/debug/fish-app/db/siyangjiaoxue.sql)
- [fix_shuizuguan.sql](D:/earnm/debug/fish-app/db/fix_shuizuguan.sql)
- [real_weiyutixing_data.sql](D:/earnm/debug/fish-app/db/real_weiyutixing_data.sql)
- [portable_shop_seed.sql](D:/earnm/debug/fish-app/db/portable_shop_seed.sql)

## Start the backend

Background:

```bat
start_backend_safe.bat
```

Foreground:

```bat
start_backend_foreground.bat
```

Stop:

```bat
stop_backend_safe.bat
```

## URLs

Frontend:

`http://127.0.0.1:8080/guanshangl0g62fht/front/index.html`

Admin:

`http://127.0.0.1:8080/guanshangl0g62fht/admin/index.html`

## Demo accounts

- Admin: `admin` / `admin`
- User: `12` / `123456`

## Writable files

Default upload/output directory:

`D:\earnm\debug\fish-app\appdata\upload`

On another computer, move the whole project folder together with `appdata`. The startup script will default to the local project path, so uploaded files and migrated images continue to work without absolute paths.
