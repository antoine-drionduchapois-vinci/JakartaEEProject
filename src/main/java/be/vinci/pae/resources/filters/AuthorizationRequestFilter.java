package be.vinci.pae.resources.filters;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Request filter for authorization.
 */
@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  @Context
  private ResourceInfo resourceInfo;
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
      .build();

  @Inject
  private UserDAO myUserDAO;

  /**
   * Filters the request to check for authorization.
   *
   * @param requestContext the container request context
   * @throws IOException if an I/O error occurs during filtering
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
    } else {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
      } catch (Exception e) {
        throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
            .entity("Malformed token : " + e.getMessage()).type("text/plain").build());
      }
      User authenticatedUser = myUserDAO.getOneByID(decodedToken.getClaim("user").asInt());
      if (authenticatedUser == null) {
        requestContext.abortWith(Response.status(Status.FORBIDDEN)
            .entity("You are forbidden to access this resource").build());
      }
      Method resourceMethod = resourceInfo.getResourceMethod(); // Obtenez la méthode de ressource demandée
      if (resourceMethod != null) {
        Authorize authorizeAnnotation = resourceMethod.getAnnotation(Authorize.class);
        if (authorizeAnnotation != null) {
          List<String> authorizedRoles = Arrays.asList(authorizeAnnotation.value());
          if (!authorizedRoles.contains(authenticatedUser.getRole().name())) {
            throw new WebApplicationException(Response.status(Status.FORBIDDEN)
                .entity("You do not have permission to access this resource").build());
          }
        }
      }

      requestContext.setProperty("user", authenticatedUser);
    }
  }

}
