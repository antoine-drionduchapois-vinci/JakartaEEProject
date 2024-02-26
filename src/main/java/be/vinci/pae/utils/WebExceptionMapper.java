package be.vinci.pae.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Provider class for mapping exceptions to appropriate HTTP responses.
 */
@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  /**
   * Maps a Throwable to an HTTP response.
   *
   * @param exception The exception to be mapped.
   * @return The HTTP response corresponding to the exception.
   */
  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();
    if (exception instanceof WebApplicationException) {
      return Response.status(((WebApplicationException) exception).getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
