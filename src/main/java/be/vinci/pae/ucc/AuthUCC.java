package be.vinci.pae.ucc;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This interface defines methods for authentication and user management.
 */
public interface AuthUCC {

  /**
   * Authenticates a user with the provided email and password.
   *
   * @param email The email of the user.
   * @param password The password of the user.
   * @return An ObjectNode containing authentication information, such as a JWT token.
   */
  ObjectNode login(String email, String password);

  /**
   * Registers a new user.
   *
   * @param user1 The user to register.
   * @return An ObjectNode containing authentication information, such as a JWT token.
   */
  ObjectNode register(User user1);

  /**
   * Creates a new User object and returns it.
   *
   * @param name The name of the user.
   * @param firstname The first name of the user.
   * @param email The email of the user.
   * @param telephone The telephone number of the user.
   * @param password The password of the user.
   * @param role The role of the user.
   * @return A User object with the provided information.
   */
  User createUserAndReturn(String name, String firstname, String email, String telephone,
      String password, String role);
}
