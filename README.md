# Building a OSLC PROMCODE server with Lyo Designer

Lyo Designer (https://oslc.github.io/developing-oslc-applications/eclipse_lyo/lyo-designer.html) is an Eclipse plugin that allows one to model (1) the information 
model of the RDF resources being defined, and (2) the individual services and 
operations of each Server in the system. The information model is defined by 
describing all types of resources for your domain, their properties and 
relationships between the types of resources and the properties. The individual 
services and operations of your server are defined by specifying a tool chain 
model. The definitions of these are done using graphical user interface. Then, 
Lyo Designer includes an integrated code generator that synthesizes the model 
into an OSLC-compliant implementation. 

In this document, you will learn how to build an OSLC PROMCODE server that 
complies with the OASIS OSLC PROMCODE Specification 1.0 with Lyo 
Designer.

A sequence of steps to build an OSLC PROMCODE server is as follows. 

1. Install Lyo Designer
2. Create a new Eclipse project
3. Create a domain specification model for PROMCODE
4. Create a toolchain model
5. Generate Source Code
6. Set up a persistent store for storing PROMCODE resources
7. Build and run the server
8. Test the server

## Install Lyo Designer

The first step is to install Lyo Designer. You can follow the installation guide to install Lyo Designer at the following site.

1. https://oslc.github.io/developing-oslc-applications/eclipse_lyo/install-lyo-designer [^1]

[^1]: Eclipse Version: 2022-03 with EMF features is used in our tutorial.

## Setup Eclipse project

For development in Eclipse, you need to create a project in your Eclipse workspace.   

0. Switch to `Modeling` perspective
1. Select File > New > `Modeling Project...`
2. Specify Project name (e.g., "promcode"). A new project is created.
3. Select File > New > Other... > `Example EMF Model Creation Wizards` > `OSLC Domain Model`, and click Next
4. Select the project "promcode" and specify File name (e.g., "domain.xml"), then click Finish button
5. Select File > New > Other... > `Lyo Designer` > `OSLC Toolchain Model`, and click Next
6. Select the project "promcode" and specify File name (e.g., "toolchain.xml") then click Finish button
7. Open the context menu on the project "promcode", then select `Viewpoint Selection`
8. Check `ToolchainViewpoint` and click OK

## Create a domain specification model of PROMCODE

OSLC PROMCODE specification defines the types of resources, their properties and relationships between the types of resources and the properties. In this step, you will create a domain specification model for OSLC PROMCODE specification with graphical model editor. In the model, you will define a number of domain specifications such as OSLC PROMCODE and Dublin Core. From the domain specification model, you can produce a library of Java classes for the resources at later step.

1. Expand "promcode" > "domain.xml" > Specification
2. Double click `SpecificationDiagram`
3. Create `Domain Specifications`. For example, drop `Domain Specification` from the Palette view and set properties as follows.
   - OSLC PROMCODE
     - Name: promcode
     - Namespace URI: http://open-services.net/ns/promcode#
     - Namespace Prefix: oslc_promcode
   - Dublin Core
     - Name: dcterms
     - Namespace URI: http://purl.org/dc/terms/
     - Namespace Prefix: dcterms
   - Example domain for external resouces
     - Name: mycompany
     - Namespace URI: http://my.company.domain/mycom#
     - Namespace Prefix: mycom
4. Add resources, and properties to complete a domain model

![Figure1: Specification Diagram](SpecificationDiagram.png)

## Create a toolchain model

The final step of modeling is to create a toolchain model in Lyo Designer. The tool chain model defines a set of interfaces between client and server. From the tool chain model, you can generate a set of Java classes that implements OSLC interfaces for the PROMCODE specification.

1. Expand "promcode" > "toolchain.xml" > "Toolchain New Toolchain"
2. Double click `ToolchainDiagram`
3. Drop `OSLC Server` from the Palette
4. Specify Name (e.g., "PROMCODE Server")
5. Expand "promcode" > "toolchain.xml" > "Toolchain New Toolchain"
6. Open context menu on `Adaptor Interface PROMCODE Server`, then select `New Representation` > `new AdapterInterfaceDiagram`
7. Specify name (e.g., "PROMCODE Server") and click OK
8. If the diagram editor is not opened automatically, double click `PROMCODE Server` under `Adaptor Interface PROMCODE Server`
9. Select `General` in `Configuration` pane
    - Set `Java Base Package Name` to appropriate name e.g. "org.promcode.sample.tool" in the Properties view
10. Select `Project Configuration` in `Configuration` pane
    - Set `Group Id` to appropriate name e.g. "org.promcode.sample" in the Properties view
11. Drop `OSLC Service` from the Palette onto the Editor
    - Select `Service Provider` then select `Domain Specification promcode`
12. Drop `Creation Factory` from the Palette to `OSLC Service` for each resource.
13. Set properties in the Properties view. For example, set the followings for ScopeItem creation factory.
    - Title: ScopeItemCreationFactory
    - Label: ScopeItemCreationFactory
    - Creation URI: createScopeItem
    - Resource Types: Resource ScopeItem
14. Drop `Store` from the Palette and set properties for Store
    - Default Named Graph: urn:x-arq:DefaultGraph
    - Sparql Query Endpoint: http://localhost:3030/dataset/sparql
    - Sparql Update Endpoint: http://localhost:3030/dataset/update
    Above settings are for Apache Jena Fuseki server that you will set up later
15. Click `Connect Service to Store` in the Palette, click `OSLC Service` in the diagram, then click `Store` in the diagram to establish a persistence connection between Store and OSLC Service
16. Check `Creation Factory` in the Properties view for the connection
17. Drop `CRUD Service` from the Palette
18. Specify properties and then click OK
    - Select a resource in the promcode domain, then click Finish button
    In the following dialog boxes
    - Set `WebService` as Class Name
    - Set `pm/resources` as service relative URL
    - Set `[ResouceType]/{id}` as method relative URL pattern
19. Set properties in the Properties view for the Web Service
    - Add all resources in the domain model except two abstract classes `ManagedItem` and `ManagedItemCollection` Resource Types. 
    - Check "Read", "Delete" and "Update"
20. Click `Connect Service to Store` in the Palette, click `WebService` in the diagram, then click `Store` in the diagram to establish a persistence connection between Store and WebService
21. Check "Read", "Delete" and "Update" in the Properties view for the connection

![Figure2: Toolchain Diagram](ToolChainDiagram.png)

![Figure3: Part of PROMCODEServer Diagram](PROMCODEServer.png)

## Generate Source Code

You can generate a set of Java source code from the model you created at the above steps.

1. Open context menu on "toolchain.xml", then select `OSLC Lyo Designer` > `Generate Complete Toolchain Java Code` [^2]
    - Select project top folder as the place to store the generated code, then finish
3. Refresh the project (e.g. press F5 key)
4. Open `RestDelegate` class by Java Editor
- Add the following code between `// Start of user code class_attributes` and `// End of user code`
``` 
    private static int scopeItemId = 1;
    private static int workItemId = 1;
    private static int artifactId = 1;
    private static int issueId = 1;
    private static int riskId = 1;
    private static int measureId = 1;
    private static int measurementId = 1;
    private static int projectId = 1;
    private static int planId = 1;
    private static int reportId = 1;
    private static int issueCollectionId = 1;
    private static int riskCollectionId = 1;

    private static ServiceProviderInfo sp = new ServiceProviderInfo();
    static {
    	sp.name = "PROMCODE Service Provider";
    	sp.serviceProviderId = "";
    }
```    
- Edit `getServiceProviderInfos` method to return the static one.
```
    // Start of user code "ServiceProviderInfo[] getServiceProviderInfos(...)"
    serviceProviderInfos = new ServiceProviderInfo[] {sp};
    // End of user code
```
- Edit `create` method for each resource. For example, in workItem case,
```
    //TODO: Set the uri of the resource to be created. Replace this code within the protected user code.
    String id = Integer.toString(workItemId++);
    uri = resourcesFactory.constructURIForWorkItem(id /* aResource.getIdentifier() */);
    if (null == uri) {
        throw new UnsupportedOperationException("Set the uri of the resource to be created, before it can be added to the store. You can then remove this UnsupportedOperationException");
    }
    aResource.setIdentifier(id);
    // End of user code
```

[^2]: Re-generating source code may produce duplicate source code. If so, remove every duplicated source code.

## Setup persistent store 

In this step, you will set up an RDF data store to store the OSLC PROMCODE resources. 

In this document, we will utilize the Apache Jena Fuseki that is an open source RDF server. You can select either Docker version or Java version.

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

## Build and run the server

In this step, you will build a server from generated source code. The server provides the user with OSLC interfaces to the features such as service catalog, create factory, query for OSLC PROMCODE resources. This step also starts the server so that you can check the behavior after the server is built.  

1. If you don't have Apache Maven installed, install Apache Maven. (We verified this tutorial with Maven version 3.9.0)
2. Go to the project top folder in a terminal (on Mac/Linux) or a command prompt (on Windows) and run the following command
3. do `mvn install`
4. do `mvn jetty:run-war` 
5. Access `http://localhost:8080/oslc-server1/`

## Test the server

You can test the server by performing resource operations.

1. create a file named scopeitem1.ttl
``` 
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix oslc_promcode: <http://open-services.net/ns/promcode#> .
<>  a oslc_promcode:ScopeItem ;  
dcterms:identifier "1" ;  
dcterms:title "SI1" ;  
dcterms:description "UI for making a reservation" .
``` 
2. `curl -d @scopeItem1.ttl -H "Accept: text/turtle;"　-H "Content-type: text/turtle" http://localhost:8080/oslc-server1/oslc/resources/createScopeItem`
3. `curl -H "Accept: text/turtle;" http://localhost:8080/oslc-server1/oslc/pm/resources/ScopeItem/1`

## References
- [OASIS OSLC PROMCODE Specification 1.0](https://docs.oasis-open.org/oslc-promcode/promcode/v1.0/os/promcode-spec.html#)