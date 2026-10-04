# Contributing — OSLC PROMCODE Server

Contributions are welcome, ensure the latest SNAPSHOT dependencies of Lyo and 
Lyo Designer are used for modelling, development, generation, and testing.

## Modelling changes

This server is generated from a Lyo Designer model (`promcode-lyo-server-model`).
Prefer making model changes in Lyo Designer to preserve traceability between the
model and the generated code.

## Before you submit

- `mvn test` should build the module (it has no automated tests yet).
- Keep `DEVELOPMENT.md` and `AGENTS.md` in sync when the build/test setup changes.
- Keep CI configuration and `DEVELOPMENT.md` in sync for any significant changes.

## Updating docs

- User-facing docs should go to the README.md
- Dev-facing docs (useful for both devs and agents) should go to the DEVELOPMENT.md
- Universal contribution docs go here (CONTRIBUTING.md)
- Agent-specific docs go to AGENTS.md
