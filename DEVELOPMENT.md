# Development — OSLC PROMCODE Server

A sample **OSLC PROMCODE 1.0** server, modelled and generated with
[Lyo Designer](https://oslc.github.io/developing-oslc-applications/eclipse_lyo/lyo-designer.html).
The single Maven module is `promcode-lyo-server/` (a WAR).

## Prerequisites
- JDK 11
- Maven 3
- (Optional) Docker / Docker Compose, for running the server and its Fuseki store

## Building
    cd promcode-lyo-server
    mvn test
The module currently has **no automated tests**; `mvn test` is a compile check.

## Running the server
The server needs an RDF store (Apache Jena Fuseki). See `README.md` for the full
Fuseki setup. Quick options:

    # built-in Jetty
    cd promcode-lyo-server && mvn clean jetty:run-war

    # Tomcat via Maven Cargo
    mvn clean cargo:run

    # Docker Compose (server + Fuseki together)
    docker-compose up --build

Then open http://localhost:8080/promcode-server/ (Fuseki on :3030).

> Docker here is for **deployment**, not tests — this repo has no Docker-based
> automated tests.
