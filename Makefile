CONFIG ?= .ai-sdlc.json
PREFIX ?= $(HOME)/.local
BINDIR ?= $(PREFIX)/bin
USE_CASE ?=
AGENT ?= coding
TASK ?= Implement or update the approved HR Policy Assistant feature from the AI specs
START_AGENT_ARGS = --config "$(CONFIG)" --agent "$(AGENT)" --task "$(TASK)"
ifneq ($(strip $(USE_CASE)),)
START_AGENT_ARGS += --use-case "$(USE_CASE)"
endif

.PHONY: help install-cli uninstall-cli verify agent-run agent-product agent-architecture agent-coding agent-test agent-security agent-release

help:
	@echo "AI Native SDLC commands"
	@echo
	@echo "  make install-cli      # Linux/macOS"
	@echo "  make verify"
	@echo "  make agent-run AGENT=coding TASK=\"Implement the approved feature\""
	@echo "  make agent-coding TASK=\"Implement the approved feature\""
	@echo "  make agent-test TASK=\"Generate AI eval tests\""
	@echo "  make agent-security TASK=\"Review the feature for AI security risks\""
	@echo "  make agent-release TASK=\"Prepare release readiness evidence\""
	@echo
	@echo "Use CONFIG=$(CONFIG) to point at another runner config."
	@echo "Use USE_CASE=path/to/use-case to override the configured default."

install-cli:
	mkdir -p "$(BINDIR)"
	ln -sf "$(CURDIR)/bin/ai-sdlc" "$(BINDIR)/ai-sdlc"
	@echo "Installed ai-sdlc to $(BINDIR)/ai-sdlc"
	@echo "Make sure $(BINDIR) is on your PATH."
	@echo "Windows PowerShell: ./scripts/install-cli.ps1"

uninstall-cli:
	rm -f "$(BINDIR)/ai-sdlc"
	@echo "Removed $(BINDIR)/ai-sdlc"

verify:
	cd sample/policy-assistant && ./gradlew test

agent-run:
	scripts/start-agent-run.sh $(START_AGENT_ARGS)

agent-product:
	$(MAKE) agent-run AGENT=product

agent-architecture:
	$(MAKE) agent-run AGENT=architecture

agent-coding:
	$(MAKE) agent-run AGENT=coding

agent-test:
	$(MAKE) agent-run AGENT=test

agent-security:
	$(MAKE) agent-run AGENT=security

agent-release:
	$(MAKE) agent-run AGENT=release
