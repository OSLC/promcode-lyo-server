# Development — OSLC PROMCODE Server

The Maven WAR module is `promcode-lyo-server/`; its Lyo Designer model is in
`promcode-lyo-server-model/`. Use JDK 25 and Maven 3.9 or newer.

## Build and tests

From `promcode-lyo-server/`:

```sh
mvn test -Dselfie=readonly
mvn clean verify -Dselfie=readonly
```

JUnit 6 tests check PROMCODE Artifact RDF round trips, resource URI encoding,
and the StorePool SPARQL HTTP endpoint using an embedded MockServer. They require
neither Docker nor an external Fuseki server. Selfie snapshots are checked into
test sources; after an intentional representation change, use `-Dselfie=overwrite`
and review the `.ss` diff before committing.

`verify` also checks test formatting with Spotless. Use `mvn spotless:apply` to
format tests. Generated main sources are excluded from automatic formatting.
Run `mvn verify -Pspotbugs` for static analysis and
`mvn rewrite:dryRun -Prewrite` to preview Java/logging recipe changes in test code.
SpotBugs currently reports `NM_SAME_SIMPLE_NAME_AS_SUPERCLASS` for the generated
`servlet.Application` class extending `jakarta.ws.rs.core.Application`; that
profile fails until the generator's class naming is changed.

The optional **Acceptance test (Lyo HEAD)** workflow builds a selected upstream
Lyo branch and then verifies this server against the locally installed artifacts.
Its default is `eclipse-lyo/lyo`, branch `main`.

## Running and configuration

See [README startup instructions](README.md#running-the-oslc-promcode-server)
for Fuseki setup and server launch commands. Compose builds the root Dockerfile;
the publication workflow pushes the same server image to GHCR for amd64 and arm64.
Jetty, Tomcat and the container deploy at `/`; OSLC routes are under `/oslc/`.

Logging uses SLF4J with `src/main/resources/logback.xml`. Set package log levels
there when diagnosing requests. Store connection defaults live in
`src/main/resources/store.properties`; the `LYO_STORE_*` environment variables
override them.

## Generated build configuration

Keep additions inside Lyo Designer user-code guards. After regeneration, check
the Java 25 compiler target, Logback binding, Tomcat installer configuration,
and pinned plugin versions in `pom.xml`: those existing generated sections may
be replaced by generator defaults. Do not format generated Java boilerplate.
