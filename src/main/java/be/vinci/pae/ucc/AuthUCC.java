package be.vinci.pae.ucc;

import be.vinci.pae.domain.UserDTO;

/**
 * This interface defines methods for authentication and user management.
 */
public interface AuthUCC {

  /**
   * Authenticates a user based on provided email and password.
   *
   * @param userTemp user dto
   * @return An ObjectNode containing authentication information,
   *         including a JWT token, user ID, and email.
   */
  UserDTO login(UserDTO userTemp);

  /**
   * Registers a new user.
   *
   * @param userTemp The user to register.
   * @return An ObjectNode containing authentication information,
   *         including a JWT token, user ID, and email.
   */
  UserDTO register(UserDTO userTemp);


}
