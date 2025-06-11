# Introduction

This is a sample OSLC PROMCODE server, that is based on the [OASIS OSLC PROMCODE Specification 1.0](https://docs.oasis-open.org/oslc-promcode/promcode/v1.0/os/promcode-spec.html#).

# Running the OSLC PROMCODE server

Follow the 3 sections below to:

1. [Setup persistent store](#setup-persistent-store)
1. [Run the OSLC Server](#run-the-oslc-server)
1. [Navigate the OSLC server](#navigate-to-oslc-server)
1. [Populate the OSLC server](#populate-the-oslc-server)

## Setup persistent store 

You will set up an RDF data store to store the OSLC PROMCODE resources. 

We will utilize the Apache Jena Fuseki that is an open source RDF server. You can select either Docker version or Java version.

### Docker version
1. Download docker version of fuseki server from https://repo1.maven.org/maven2/org/apache/jena/jena-fuseki-docker/4.2.0/
2. `docker-compose build --build-arg JENA_VERSION=3.16.0`
3. `docker-compose run --rm --service-ports fuseki --update --mem /dataset`
### Java version
1. Download jar file from https://repo1.maven.org/maven2/org/apache/jena/jena-fuseki-server/ 
2. For the latest Java version, run the following command
   - `java -cp jena-fuseki-server-4.2.0.jar org.apache.jena.fuseki.main.cmds.FusekiMainCmd --update --mem /dataset`
3. For Java 1.8 , run the following command
   - `java -cp jena-fuseki-server-3.16.0.jar org.apache.jena.fuseki.main.cmds.FusekiMainCmd --update --mem /dataset`


## Run the OSLC Server
There are multiple options to run the OSLC Server.
Below are the simplest options if you don't want to use anything except JDK and a Maven installation. Prerequisites:

- JDK 11
- Maven 3

### Using built-in servers directly

Follow these steps to start the server:

```sh
cd promcode-lyo-server
mvn clean jetty:run-war
```

Now proceed to the steps listed in the next section _Navigating OSLC servers_.

### Running on Tomcat via Maven

Follow these steps to start the server:

```sh
cd promcode-lyo-server
mvn clean cargo:run
```

## Navigate to OSLC server

The OSLC server is available at the following URL:

- http://localhost:8080/promcode-server/

From that point, you can navigate through the Service Provider Catalog, or using the built-in Swagger Editor.

## Populate the OSLC server

THe server contains no data at startup. 
You can add resources to the server by performing resource operations on their creation factories.

This can be done through the built-in Swagger editor, or using the command line. 

**Bash/Linux/macOS:**
```bash
curl -i --data-raw '@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix oslc_promcode: <http://open-services.net/ns/promcode#> .
<> a oslc_promcode:Artifact ;
   dcterms:identifier "1" ;
   dcterms:title "A1" ;
   dcterms:description "UI for making a reservation" .' \
-H "Accept: text/turtle;" \
-H "Content-type: text/turtle" \
http://localhost:8080/promcode-server/oslc/service1/artifacts/create
```

**PowerShell/Windows:**
```powershell
curl -i --data-raw '@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix oslc_promcode: <http://open-services.net/ns/promcode#> .
<> a oslc_promcode:Artifact ;
   dcterms:identifier "1" ;
   dcterms:title "A1" ;
   dcterms:description "UI for making a reservation" .' `
-H "Accept: text/turtle;" `
-H "Content-type: text/turtle" `
http://localhost:8080/promcode-server/oslc/service1/artifacts/create
```

2. To retrieve the created artifact:
   ```bash
   curl -H "Accept: text/turtle;" http://localhost:8080/promcode-server/oslc/artifact/1
   ```


# Modifying the OSLC PROMCODE server with Lyo Designer

This server is modelled and generated using [Lyo Designer](https://oslc.github.io/developing-oslc-applications/eclipse_lyo/lyo-designer.html). 

It is recommended that any changes to the code should be done using LyoDesigner to maintain traceability between the model and the code. Below are some instructions on how to open the projects in this repository with LyoDesigner.

It is recommended you get familiar with the LyoDesigner tool before you perform any changes.

To open the relevant projects for the OSLC PROMCODE server, you need to follow the steps below.

## Install Lyo Designer

The first step is to [install Lyo Designer](https://oslc.github.io/developing-oslc-applications/eclipse_lyo/install-lyo-designer).

## Import the PROMCODE domain model

The PROMCODE domain model is already modelled and made available to be used by any PROMCODE server installation.

1. Import the [Lyo Git repository](https://github.com/eclipse/lyo.git)
1. In LyoDesigner, import the Modelling project under the "domains\org.eclipse.lyo.tools.domainmodels" folder.

This project contains the PROMCODE model, and all other OSLC Domain Specifications.

## Import the PROMCODE OSLC Server model
In LyoDesigner, import the Modelling project under the "promcode-lyo-server-model" folder.

This model depends on the PROMCODE domain model, imported in the previous step. It is important the domain model is imported first.

This models a simple PROMCODE server, which can be used as a basis for your own extensions and modifications.

## Import the PROMCODE OSLC Server
1. In LyoDesigner, import the Maven project under the "promcode-lyo-server" folder.

This maven project is generated from the server model imported above.

# References
- [OASIS OSLC PROMCODE Specification 1.0](https://docs.oasis-open.org/oslc-promcode/promcode/v1.0/os/promcode-spec.html#)