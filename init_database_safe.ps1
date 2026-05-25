param()

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$bootstrapSql = Join-Path $projectRoot 'db\minimal_bootstrap.sql'
$realContentSql = Join-Path $projectRoot 'db\real_content_data.sql'
$newsSql = Join-Path $projectRoot 'db\news_portable.sql'
$forumSql = Join-Path $projectRoot 'db\fix_forum.sql'
$shopSql = Join-Path $projectRoot 'db\remaining_data.sql'
$teachingSql = Join-Path $projectRoot 'db\siyangjiaoxue.sql'
$aquariumSql = Join-Path $projectRoot 'db\fix_shuizuguan.sql'
$reminderSql = Join-Path $projectRoot 'db\real_weiyutixing_data.sql'
$portableShopSeedSql = Join-Path $projectRoot 'db\portable_shop_seed.sql'

if (-not $env:APP_HOME) {
    $env:APP_HOME = $projectRoot
}

if (-not $env:APP_FILES_DIR) {
    $env:APP_FILES_DIR = Join-Path $projectRoot 'appdata'
}

if (-not $env:APP_DB_USERNAME) {
    $env:APP_DB_USERNAME = 'root'
}

if (-not $env:APP_DB_PASSWORD) {
    $env:APP_DB_PASSWORD = '971021'
}

function Get-MysqlCommand {
    if ($env:MYSQL_HOME) {
        $mysqlFromHome = Join-Path $env:MYSQL_HOME 'bin\mysql.exe'
        if (Test-Path $mysqlFromHome) {
            return $mysqlFromHome
        }
    }

    $mysqlCmd = Get-Command mysql -ErrorAction SilentlyContinue
    if ($mysqlCmd) {
        return $mysqlCmd.Source
    }

    $commonMysql = 'C:\Program Files\MySQL\MySQL Server 9.6\bin\mysql.exe'
    if (Test-Path $commonMysql) {
        return $commonMysql
    }

    throw 'mysql client not found. Install MySQL client and set MYSQL_HOME or add mysql to PATH.'
}

function Get-DbConfig {
    $config = [ordered]@{
        Host = '127.0.0.1'
        Port = '3306'
        Database = 'guanshangl0g62fht'
        Username = if ($env:APP_DB_USERNAME) { $env:APP_DB_USERNAME } else { 'root' }
        Password = if ($env:APP_DB_PASSWORD) { $env:APP_DB_PASSWORD } else { '971021' }
    }

    if ($env:APP_DB_URL -match '^jdbc:mysql://(?<host>[^:/?]+)(:(?<port>\d+))?/(?<db>[^?]+)') {
        $config.Host = $matches.host
        if ($matches.port) { $config.Port = $matches.port }
        $config.Database = $matches.db
    }

    return $config
}

foreach ($sqlPath in @($bootstrapSql, $realContentSql, $newsSql, $forumSql, $shopSql, $teachingSql, $aquariumSql, $reminderSql, $portableShopSeedSql)) {
    if (-not (Test-Path $sqlPath)) {
        throw "Missing SQL file: $sqlPath"
    }
}

$mysqlExe = Get-MysqlCommand
$dbConfig = Get-DbConfig

$args = @(
    '--default-character-set=utf8mb4',
    '-h', $dbConfig.Host,
    '-P', $dbConfig.Port,
    '-u', $dbConfig.Username
)

if ($dbConfig.Password) {
    $args += "-p$($dbConfig.Password)"
}

$appFilesDir = $env:APP_FILES_DIR
$uploadDir = Join-Path $appFilesDir 'upload'
$outputDir = Join-Path $appFilesDir 'output'
New-Item -ItemType Directory -Force -Path $uploadDir | Out-Null
New-Item -ItemType Directory -Force -Path $outputDir | Out-Null

$sqlFiles = @($bootstrapSql, $realContentSql, $newsSql, $forumSql, $shopSql, $teachingSql, $aquariumSql, $reminderSql, $portableShopSeedSql)
$sourceScript = New-Object System.Collections.Generic.List[string]
$sourceScript.Add('SET NAMES utf8mb4;')
foreach ($sqlFile in $sqlFiles) {
    $sourceScript.Add("-- BEGIN $([System.IO.Path]::GetFileName($sqlFile))")
    $sourceScript.Add((Get-Content -Path $sqlFile -Encoding UTF8 -Raw))
    $sourceScript.Add("-- END $([System.IO.Path]::GetFileName($sqlFile))")
}

$combinedSqlPath = Join-Path $projectRoot 'db\_combined_init.sql'
Set-Content -Path $combinedSqlPath -Value ($sourceScript -join [Environment]::NewLine) -Encoding UTF8

$mysqlArgLine = ($args | ForEach-Object {
    if ($_ -match '\s') {
        '"' + $_.Replace('"', '\"') + '"'
    } else {
        $_
    }
}) -join ' '

try {
    cmd /c "`"$mysqlExe`" $mysqlArgLine < `"$combinedSqlPath`""
} finally {
    if (Test-Path $combinedSqlPath) {
        Remove-Item -LiteralPath $combinedSqlPath -Force
    }
}

if ($LASTEXITCODE -ne 0) {
    throw "Database initialization failed with exit code $LASTEXITCODE"
}

"DATABASE READY: $($dbConfig.Database)@$($dbConfig.Host):$($dbConfig.Port)"
