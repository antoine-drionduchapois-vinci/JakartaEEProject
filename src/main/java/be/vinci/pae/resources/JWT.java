package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserDTO.Role;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface for JWT (JSON Web Token) operations.
 */
public interface JWT {

  /**
   * Creates a JWT token for the given user DTO.
   *
   * @param userDTO the user DTO for which the token is created
   * @return an ObjectNode containing the JWT token and user information
   */
  ObjectNode createToken(UserDTO userDTO);

  /**
   * Retrieves user ID from string token.
   *
   * @param token the token to be decrypted
   * @return user ID
   */
  int getUserIdFromToken(String token);

  Role getRoleFromToken(String token);

}
