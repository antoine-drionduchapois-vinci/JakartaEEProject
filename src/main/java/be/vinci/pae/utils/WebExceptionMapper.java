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

  @Override
  public Response toResponse(Throwable exception) {
    if (exception instanceof WebApplicationException exc) {
      System.err.println(exc.getResponse().getStatus() + " " + exc.getMessage());
      return Response.status(exc.getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof BusinessException exc) {
      System.err.println(exc.getCode() + " " + exc.getMessage());
      return Response.status(((BusinessException) exception).getCode())
          .entity(exception.getMessage()).build();
    }

    System.err.println("500 " + exception.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
