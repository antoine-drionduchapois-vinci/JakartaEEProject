package be.vinci.pae.utils;

import be.vinci.pae.resources.UserResource;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provider class for mapping exceptions to appropriate HTTP responses.
 */
@Provider
public class WebExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger logger = LogManager.getLogger(UserResource.class);

  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();

    if (exception instanceof WebApplicationException exc) {
      logger.error("Status: " + exc.getResponse().getStatus() + " " + exc.getMessage());

      return Response.status(exc.getResponse().getStatus())
          .entity(exception.getMessage())
          .build();
    }

    if (exception instanceof BusinessException exc) {
      logger.error("Status: " + exc.getCode() + " " + exc.getMessage());
      return Response.status(((BusinessException) exception).getCode())
          .entity(exception.getMessage()).build();
    }

    if (exception instanceof NotFoundException exc) {
      logger.error("Status: 404 " + exc.getMessage());
      return Response.status(404).entity("Not Found").build();
    }

    logger.error("Status: 500 " + exception.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
