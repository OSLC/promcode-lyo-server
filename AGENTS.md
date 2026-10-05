# AGENTS.md — OSLC PROMCODE Server

Follow [DEVELOPMENT.md](DEVELOPMENT.md) and [CONTRIBUTING.md](CONTRIBUTING.md).

- Run `mvn test -Dselfie=readonly` in `promcode-lyo-server/` for the default check.
- Preserve Lyo Designer user-code guards; do not rewrite generated Java outside them.
- Prefer Java text blocks for multiline RDF payloads.
- Run container smoke checks only when Docker or Podman is available.
