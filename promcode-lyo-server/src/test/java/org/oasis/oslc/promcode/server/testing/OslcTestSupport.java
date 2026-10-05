package org.oasis.oslc.promcode.server.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.lyo.oslc4j.provider.jena.JenaModelHelper;

/** Test-only helpers for Lyo 7 POJOs and ordinary RDF graphs. */
public final class OslcTestSupport {
  private OslcTestSupport() {}

  public static Model parse(String rdf, String syntax) {
    Model model = ModelFactory.createDefaultModel();
    try {
      model.read(new StringReader(rdf), null, syntax);
      return model;
    } catch (RuntimeException failure) {
      model.close();
      throw failure;
    }
  }

  public static void assertIsomorphic(String expected, String actual, String syntax) {
    Model expectedModel = parse(expected, syntax);
    try {
      Model actualModel = parse(actual, syntax);
      try {
        assertTrue(expectedModel.isIsomorphicWith(actualModel), "RDF graphs differ");
      } finally {
        actualModel.close();
      }
    } finally {
      expectedModel.close();
    }
  }

  public static <T> T readOne(String rdf, String syntax, Class<T> type) throws Exception {
    Model model = parse(rdf, syntax);
    try {
      T[] resources = JenaModelHelper.unmarshal(model, type);
      assertEquals(1, resources.length, "Expected exactly one " + type.getSimpleName());
      return resources[0];
    } finally {
      model.close();
    }
  }

  public static String pojoRdf(Object resource) throws Exception {
    Model model = JenaModelHelper.createJenaModel(new Object[] {resource});
    try {
      StringWriter writer = new StringWriter();
      model.write(writer, "RDF/XML");
      return writer.toString();
    } finally {
      model.close();
    }
  }

  public static String rdfSnapshot(String rdf, String syntax, String mockOrigin) {
    // Parse explicitly first: the borrowed normalizer's text fallback must not hide bad RDF.
    Model model = parse(rdf, syntax);
    String normalized;
    try {
      StringWriter writer = new StringWriter();
      model.write(writer, "RDF/XML");
      normalized = SnapshotUtils.stabilizeRdf(writer.toString());
    } finally {
      model.close();
    }
    return mockOrigin == null ? normalized : normalized.replace(mockOrigin, "http://example.test");
  }

  public static String fixture(String name, Map<String, String> replacements) throws IOException {
    try (var input = OslcTestSupport.class.getResourceAsStream("/fixtures/" + name)) {
      if (input == null) throw new IOException("Missing fixture: " + name);
      String content = new String(input.readAllBytes(), StandardCharsets.UTF_8);
      for (var replacement : replacements.entrySet()) {
        content = content.replace("{{" + replacement.getKey() + "}}", replacement.getValue());
      }
      if (content.matches("(?s).*\\{\\{[^}]+}}.*")) {
        throw new IOException("Unresolved placeholder in fixture: " + name);
      }
      return content;
    }
  }
}
