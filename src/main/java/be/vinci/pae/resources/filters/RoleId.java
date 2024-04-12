package be.vinci.pae.resources.filters;

/**
 * The RoleId interface represents a service for choosing the
 * appropriate user ID based on the user's role and provided ID.
 */
public interface RoleId {

  /**
   * Chooses the appropriate user ID based on the user's role and provided ID.
   *
   * @param token The JWT token.
   * @param id    The user ID.
   * @return The chosen user ID.
   */
  int chooseId(String token, int id);
}
