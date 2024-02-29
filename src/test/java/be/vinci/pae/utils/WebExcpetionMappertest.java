package be.vinci.pae.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.Test;

class WebExceptionMapperTest {

  @Test
  void toResponse_WebApplicationException() {
    // Arrange
    WebApplicationException exception = mock(WebApplicationException.class);
    Response response = Response.status(Status.NOT_FOUND).entity("Not found").build();
    when(exception.getResponse()).thenReturn(response);

    WebExceptionMapper mapper = new WebExceptionMapper();

    // Act
    Response result = mapper.toResponse(exception);
    System.out.println(result);

    // Assert
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
  }

  @Test
  void toResponse_GenericException() {
    // Arrange
    Exception exception = new Exception("Test exception");

    WebExceptionMapper mapper = new WebExceptionMapper();

    // Act
    Response result = mapper.toResponse(exception);
    System.out.println(result);

    // Assert
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getStatus());
    assertEquals("Test exception", result.getEntity());
  }

  @Test
  void toResponse_PrintStackTrace() {
    // Arrange
    Exception exception = new Exception("Test exception");

    WebExceptionMapper mapper = new WebExceptionMapper();

    // Act
    Response result = mapper.toResponse(exception);
    System.out.println(result);

    // Assert
    // Verify that the stack trace has been printed
    assertTrue(result.getEntity().toString().contains("Test exception"));
  }
}