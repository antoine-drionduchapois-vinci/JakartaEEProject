package be.vinci.pae.api.filters;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CorsFilterTest {

  private CorsFilter corsFilter;
  private ContainerRequestContext requestContext;
  private ContainerResponseContext responseContext;

  @BeforeEach
  void setUp() {
    corsFilter = new CorsFilter();
    requestContext = mock(ContainerRequestContext.class);
    responseContext = mock(ContainerResponseContext.class);

    // Initialize headers map to avoid NullPointerException
    MultivaluedMap<String, Object> headers = mock(MultivaluedHashMap.class);
    when(responseContext.getHeaders()).thenReturn(headers);
  }

  @Test
  void filter_AddsCorsHeaders() {
    // Appeler la méthode filter() sur l'instance CorsFilter créée dans setUp()
    corsFilter.filter(requestContext, responseContext);

    // Vérifier chaque ajout d'en-tête
    verify(responseContext, times(4)).getHeaders();
    verify(responseContext.getHeaders()).add(eq("Access-Control-Allow-Origin"), eq("*"));
    verify(responseContext.getHeaders()).add(eq("Access-Control-Allow-Headers"),
        eq("origin, content-type, accept, authorization"));
    verify(responseContext.getHeaders()).add(eq("Access-Control-Allow-Credentials"), eq("true"));
    verify(responseContext.getHeaders()).add(eq("Access-Control-Allow-Methods"),
        eq("GET, POST, PUT, DELETE, OPTIONS, HEAD"));
  }
}
