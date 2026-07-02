param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$CliArgs
)

$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot

function Get-PythonCommand {
    $python = Get-Command python3 -ErrorAction SilentlyContinue
    if ($python) { return $python.Source }

    $python = Get-Command python -ErrorAction SilentlyContinue
    if ($python) { return $python.Source }

    throw "Python 3 was not found on PATH."
}

function Resolve-AiSdlcConfig {
    $configured = if ($env:AI_SDLC_CONFIG) { $env:AI_SDLC_CONFIG } else { ".ai-sdlc.json" }
    if ([System.IO.Path]::IsPathRooted($configured)) {
        return $configured
    }

    $cwdConfig = Join-Path (Get-Location) $configured
    if (Test-Path -LiteralPath $cwdConfig) {
        return (Resolve-Path -LiteralPath $cwdConfig).Path
    }

    return (Join-Path $RepoRoot $configured)
}

function Show-Usage {
    @"
Usage:
  ai-sdlc <command> [options]

Commands:
  start        Create an agent run folder from the configured agent registry
  run          Run a generated agent prompt with Codex, Claude Code, Cursor, or manual mode
  state        Record state for an agent run
  config       Print the resolved runner config path
  help         Show this help

Examples:
  ai-sdlc start --agent coding --task "Implement the approved feature"
  ai-sdlc run --runner claude --dry-run sample/ai-docs/policy-assistant/16-agent-runs/<run-id>
  ai-sdlc state --run-dir sample/ai-docs/policy-assistant/16-agent-runs/<run-id> --status ready_for_human_review --summary "Ready"

Environment:
  AI_SDLC_CONFIG  Config path. Default: .ai-sdlc.json in the current repo, then the installed repo.
"@
}

$Python = Get-PythonCommand
$Command = if ($CliArgs.Count -gt 0) { $CliArgs[0] } else { "help" }
$Rest = if ($CliArgs.Count -gt 1) { $CliArgs[1..($CliArgs.Count - 1)] } else { @() }

switch ($Command) {
    "start" {
        & $Python (Join-Path $RepoRoot "scripts/start-agent-run.py") "--config" (Resolve-AiSdlcConfig) @Rest
        exit $LASTEXITCODE
    }
    "run" {
        & $Python (Join-Path $RepoRoot "scripts/run-agent.py") "--config" (Resolve-AiSdlcConfig) @Rest
        exit $LASTEXITCODE
    }
    "state" {
        & $Python (Join-Path $RepoRoot "scripts/record-agent-state.py") @Rest
        exit $LASTEXITCODE
    }
    "config" {
        Resolve-AiSdlcConfig
    }
    { $_ -in @("help", "-h", "--help") } {
        Show-Usage
    }
    default {
        Write-Error "Unknown command: $Command"
        Show-Usage
        exit 2
    }
}
