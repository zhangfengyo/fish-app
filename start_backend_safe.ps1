param(
    [ValidateSet('background','foreground','status','stop')]
    [string]$Mode = 'background'
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$targetDir = Join-Path $projectRoot 'target'
$jarPath = Join-Path $targetDir 'guanshangl0g62fht-0.0.1-SNAPSHOT.jar'
$stdoutLog = Join-Path $targetDir 'backend.out.log'
$stderrLog = Join-Path $targetDir 'backend.err.log'
$defaultFilesDir = Join-Path $projectRoot 'appdata'

$serverPort = if ($env:SERVER_PORT) { $env:SERVER_PORT } else { '8080' }
$contextPath = if ($env:SERVER_CONTEXT_PATH) { $env:SERVER_CONTEXT_PATH } else { '/guanshangl0g62fht' }
$healthUrl = "http://127.0.0.1:$serverPort$contextPath/news/list?page=1&limit=1"

if (-not $env:APP_HOME) {
    $env:APP_HOME = $projectRoot
}

if (-not $env:APP_FILES_DIR) {
    $env:APP_FILES_DIR = $defaultFilesDir
}

if (-not $env:APP_DB_USERNAME) {
    $env:APP_DB_USERNAME = 'root'
}

if (-not $env:APP_DB_PASSWORD) {
    $env:APP_DB_PASSWORD = '971021'
}

New-Item -ItemType Directory -Force -Path (Join-Path $env:APP_FILES_DIR 'upload') | Out-Null
New-Item -ItemType Directory -Force -Path (Join-Path $env:APP_FILES_DIR 'output') | Out-Null

function Get-JavaCommand {
    if ($env:JAVA_HOME) {
        $javaFromHome = Join-Path $env:JAVA_HOME 'bin\java.exe'
        if (Test-Path $javaFromHome) {
            return $javaFromHome
        }
    }

    $javaCmd = Get-Command java -ErrorAction SilentlyContinue
    if ($javaCmd) {
        return $javaCmd.Source
    }

    throw 'Java not found. Install JDK 8+ and set JAVA_HOME or add java to PATH.'
}

function Get-BackendProcessId {
    try {
        $connections = Get-NetTCPConnection -LocalPort ([int]$serverPort) -State Listen -ErrorAction Stop
        if ($connections) {
            return ($connections | Select-Object -First 1).OwningProcess
        }
    } catch {
    }
    return $null
}

function Test-Backend {
    try {
        $status = (Invoke-WebRequest -Uri $healthUrl -UseBasicParsing -TimeoutSec 8).StatusCode
        return $status -eq 200
    } catch {
        return $false
    }
}

function Get-LatestSourceWriteTimeUtc {
    $paths = @(
        (Join-Path $projectRoot 'src\main\java'),
        (Join-Path $projectRoot 'src\main\resources'),
        (Join-Path $projectRoot 'pom.xml'),
        (Join-Path $projectRoot 'mvnw.cmd')
    )

    $latest = [datetime]::MinValue
    foreach ($path in $paths) {
        if (-not (Test-Path $path)) {
            continue
        }

        if ((Get-Item $path).PSIsContainer) {
            $candidate = Get-ChildItem -Path $path -Recurse -File -ErrorAction SilentlyContinue |
                Sort-Object LastWriteTimeUtc -Descending |
                Select-Object -First 1
            if ($candidate -and $candidate.LastWriteTimeUtc -gt $latest) {
                $latest = $candidate.LastWriteTimeUtc
            }
            continue
        }

        $item = Get-Item $path
        if ($item.LastWriteTimeUtc -gt $latest) {
            $latest = $item.LastWriteTimeUtc
        }
    }

    return $latest
}

function Ensure-Jar {
    $needsBuild = -not (Test-Path $jarPath)

    if (-not $needsBuild) {
        $jarWriteTime = (Get-Item $jarPath).LastWriteTimeUtc
        $latestSourceWriteTime = Get-LatestSourceWriteTimeUtc
        if ($latestSourceWriteTime -gt $jarWriteTime) {
            $needsBuild = $true
        }
    }

    if (-not $needsBuild) {
        return
    }

    Push-Location $projectRoot
    try {
        cmd /c "mvnw.cmd -q -DskipTests package"
    } finally {
        Pop-Location
    }

    if (-not (Test-Path $jarPath)) {
        throw "Build failed. Missing jar: $jarPath"
    }
}

switch ($Mode) {
    'status' {
        if (Test-Backend) { 'RUNNING' } else { 'STOPPED' }
        break
    }
    'stop' {
        $existingPid = Get-BackendProcessId
        if ($existingPid) {
            Stop-Process -Id $existingPid -Force
            "STOPPED $existingPid"
        } else {
            'STOPPED none'
        }
        break
    }
    default {
        $javaExe = Get-JavaCommand
        Ensure-Jar

        if ($Mode -eq 'foreground') {
            & $javaExe '-jar' $jarPath
            break
        }

        $existingPid = Get-BackendProcessId
        if ($existingPid) {
            try { Stop-Process -Id $existingPid -Force } catch {}
            Start-Sleep -Seconds 2
        }

        if (-not (Test-Path $targetDir)) {
            New-Item -ItemType Directory -Path $targetDir | Out-Null
        }

        Start-Process -FilePath $javaExe `
            -ArgumentList @('-jar', $jarPath) `
            -WorkingDirectory $projectRoot `
            -RedirectStandardOutput $stdoutLog `
            -RedirectStandardError $stderrLog `
            -WindowStyle Hidden

        Start-Sleep -Seconds 12

        if (Test-Backend) {
            'STARTED 200'
        } else {
            'FAILED'
            if (Test-Path $stdoutLog) { Get-Content $stdoutLog -Tail 30 }
            if (Test-Path $stderrLog) { Get-Content $stderrLog -Tail 30 }
            exit 1
        }
    }
}
