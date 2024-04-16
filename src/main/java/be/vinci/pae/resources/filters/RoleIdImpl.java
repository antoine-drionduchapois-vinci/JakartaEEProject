package be.vinci.pae.resources.filters;

import be.vinci.pae.domain.UserDTO.Role;
import be.vinci.pae.resources.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

/**
 * Implementation of the RoleId interface.
 */
public class RoleIdImpl implements RoleId {

  @Inject
  private Jwt myJwt;

  /**
   * Chooses the appropriate user ID based on the user's role and provided ID.
   *
   * @param token The JWT token.
   * @param id    The user ID.
   * @return The chosen user ID.
   * @throws WebApplicationException If the user ID cannot be
   *                                 determined based on the role and ID provided.
   */
  @Override
  public int chooseId(String token, int id) {
    int userId = 0;
    if (myJwt.getRoleFromToken(token).equals("STUDENT")) {
      userId = myJwt.getUserIdFromToken(token);
      if (id == -1) {
        id = userId;
      }
      if (userId != id) {
        throw new WebApplicationException("error student id", Status.NOT_FOUND);
      }
    } else if (id != -1) {
      userId = id;
    }
    return userId;
  }
}
