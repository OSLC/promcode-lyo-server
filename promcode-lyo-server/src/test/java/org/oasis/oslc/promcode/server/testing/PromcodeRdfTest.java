package org.oasis.oslc.promcode.server.testing;

import static com.diffplug.selfie.Selfie.expectSelfie;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import org.eclipse.lyo.oslc.domains.promcode.Artifact;
import org.junit.jupiter.api.Test;
import org.oasis.oslc.promcode.server.ResourcesFactory;

class PromcodeRdfTest {
  @Test
  void artifactRoundTripPreservesPromcodeTypeAndProperties() throws Exception {
    Artifact artifact =
        new ResourcesFactory("http://example.test/promcode-server/oslc/").createArtifact("1");
    artifact.setIdentifier("1");
    artifact.setTitle("A1");
    artifact.setDescription("UI for making a reservation");
    String rdf = OslcTestSupport.pojoRdf(artifact);
    Artifact restored = OslcTestSupport.readOne(rdf, "RDF/XML", Artifact.class);
    assertEquals(artifact.getAbout(), restored.getAbout());
    assertEquals(artifact.getTitle(), restored.getTitle());
    assertEquals(artifact.getIdentifier(), restored.getIdentifier());
    assertEquals(artifact.getDescription(), restored.getDescription());
    String expected =
        """
        @prefix dcterms: <http://purl.org/dc/terms/> .
        @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
        <http://example.test/promcode-server/oslc/artifact/1>
          a <http://open-services.net/ns/promcode#Artifact>;
          dcterms:identifier "1"; dcterms:title "A1"^^rdf:XMLLiteral;
          dcterms:description "UI for making a reservation"^^rdf:XMLLiteral .
        """;
    var expectedModel = OslcTestSupport.parse(expected, "TURTLE");
    var actualModel = OslcTestSupport.parse(rdf, "RDF/XML");
    try {
      assertTrue(expectedModel.isIsomorphicWith(actualModel), rdf);
    } finally {
      expectedModel.close();
      actualModel.close();
    }
    expectSelfie(OslcTestSupport.rdfSnapshot(rdf, "RDF/XML", null)).toMatchDisk();
  }

  @Test
  void artifactIdentifierIsEncodedAsOnePathSegment() {
    ResourcesFactory factory = new ResourcesFactory("http://example.test/promcode-server/oslc/");
    assertEquals(
        URI.create("http://example.test/promcode-server/oslc/artifact/A%2FB%20C"),
        factory.constructURIForArtifact("A/B C"));
  }
}
