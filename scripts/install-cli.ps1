param(
    [string]$InstallDir = (Join-Path $HOME ".local\bin")
)

$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot
$InstallPath = [System.IO.Path]::GetFullPath($InstallDir)

New-Item -ItemType Directory -Force -Path $InstallPath | Out-Null

$RepoRootLiteral = $RepoRoot -replace "'", "''"
$ShimPs1 = @"
`$RepoRoot = '$RepoRootLiteral'
& "`$RepoRoot\bin\ai-sdlc.ps1" @args
exit `$LASTEXITCODE
"@

$ShimCmd = @"
@echo off
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0ai-sdlc.ps1" %*
"@

Set-Content -LiteralPath (Join-Path $InstallPath "ai-sdlc.ps1") -Value $ShimPs1 -Encoding UTF8
Set-Content -LiteralPath (Join-Path $InstallPath "ai-sdlc.cmd") -Value $ShimCmd -Encoding ASCII

Write-Host "Installed ai-sdlc to $InstallPath"
Write-Host "Add $InstallPath to PATH if it is not already there."
