package org.oasis.oslc.promcode.server.testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.net.URI;
import org.eclipse.lyo.store.StorePool;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

class StoreEndpointTest {
  @Test
  void configuredStoreQueriesTheNamedGraphAndSubject() {
    ClientAndServer mock = ClientAndServer.startClientAndServer(0);
    try {
      mock.when(request().withPath("/ds/sparql"))
          .respond(
              response()
                  .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                  .withBody("{\"head\":{},\"boolean\":true}"));
      URI graph = URI.create("urn:x-arq:DefaultGraph");
      String endpoint = "http://localhost:" + mock.getLocalPort() + "/ds/";
      StorePool pool =
          new StorePool(
              1,
              graph,
              URI.create(endpoint + "sparql"),
              URI.create(endpoint + "update"),
              null,
              null);
      var store = pool.getStore();
      try {
        assertTrue(store.resourceExists(graph, URI.create("http://example.test/artifact/1")));
      } finally {
        pool.releaseStore(store);
      }
      var requests = mock.retrieveRecordedRequests(request().withPath("/ds/sparql"));
      assertEquals(1, requests.length);
      String query = requests[0].getFirstQueryStringParameter("query");
      assertNotNull(query);
      assertTrue(query.contains("urn:x-arq:DefaultGraph"));
      assertTrue(query.contains("http://example.test/artifact/1"));
    } finally {
      mock.stop();
    }
  }
}
