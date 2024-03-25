package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Implementation of JWT (JSON Web Token) interface.
 */
public class JWTImpl implements JWT {

  // Algorithm used for JWT token generation
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  // ObjectMapper for JSON processing
  private final ObjectMapper jsonMapper = new ObjectMapper();

  /**
   * Creates a JWT token for the given user DTO.
   *
   * @param userDTO the user DTO for which the token is created
   * @return an ObjectNode containing the JWT token and user information
   */
  @Override
  public ObjectNode createToken(UserDTO userDTO) {
    String token;
    try {
      // Create the JWT token
      token = com.auth0.jwt.JWT.create().withIssuer("auth0")
          .withClaim("user", userDTO.getUserId()).sign(this.jwtAlgorithm);

      // Create and return an ObjectNode containing token and user information
      return jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", userDTO.getUserId())
          .put("email", userDTO.getEmail())
          .put("name", userDTO.getName())
          .put("telephone", userDTO.getPhone())
          .put("annee", userDTO.getYear())
          .put("role", userDTO.getRole().name());
    } catch (Exception e) {
      // Log error message if token creation fails
      System.out.println("Unable to create token: " + e.getMessage());
      return null;
    }
  }
}
