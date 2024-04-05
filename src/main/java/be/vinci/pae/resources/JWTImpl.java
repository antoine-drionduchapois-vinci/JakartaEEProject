package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
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
      return null;
    }
  }

  /**
   * Retrieves the user ID from a JWT token.
   *
   * @param token The JWT token.
   * @return The user ID extracted from the token, or 0 if the token is invalid or missing.
   */
  @Override
  public int getUserIdFromToken(String token) {
    try {
      JWTVerifier verifier = com.auth0.jwt.JWT.require(jwtAlgorithm).withIssuer("auth0").build();
      DecodedJWT decodedJWT = verifier.verify(token);
      return decodedJWT.getClaim("user").asInt();
    } catch (JWTVerificationException e) {
      return 0;
    }
  }

  @Override
  public String getUserEmailFromToken(String token) {
    try {
      JWTVerifier verifier = com.auth0.jwt.JWT.require(jwtAlgorithm).withIssuer("auth0").build();
      DecodedJWT decodedJWT = verifier.verify(token);
      return decodedJWT.getClaim("email").asString();
    } catch (JWTVerificationException e) {
      return null;
    }
  }
}
