#!/usr/bin/env python3
"""Update a durable AI Native SDLC agent run state."""

from __future__ import annotations

import argparse
import json
from datetime import datetime, timezone
from pathlib import Path


def utc_now() -> str:
    return datetime.now(timezone.utc).replace(microsecond=0).isoformat().replace("+00:00", "Z")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Update a durable AI Native SDLC agent run state.",
    )
    parser.add_argument("--run-dir", required=True)
    parser.add_argument("--status", required=True)
    parser.add_argument("--summary", required=True)
    parser.add_argument("--actor")
    parser.add_argument("--runner")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    run_dir = Path(args.run_dir)
    state_file = run_dir / "state.json"
    events_file = run_dir / "events.md"

    if not state_file.is_file():
        raise SystemExit(f"State file not found: {state_file}")

    state = json.loads(state_file.read_text(encoding="utf-8"))
    now = utc_now()
    state["status"] = args.status
    state["updatedAtUtc"] = now
    state["lastSummary"] = args.summary
    if args.actor:
        state["lastActor"] = args.actor
    if args.runner:
        state["lastRunner"] = args.runner

    state_file.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

    with events_file.open("a", encoding="utf-8") as file:
        file.write(f"\n## {now} - {args.status}\n\n")
        if args.actor:
            file.write(f"Actor: {args.actor}\n")
        file.write(f"{args.summary}\n")

    print(f"Recorded state '{args.status}' in {run_dir}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
