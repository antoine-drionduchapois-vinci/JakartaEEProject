package be.vinci.pae.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Utility class for decrypting JWT tokens.
 * This class provides the functionality to extract the user ID encoded within a JWT token.
 * It uses the HMAC256 algorithm for token verification and requires the JWT secret
 * configured in the system's configuration.
 */
public class JWTDecryptToken {

  /**
   * The algorithm used for JWT token verification, configured to use HMAC256.
   */
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));

  /**
   * Extracts and returns the user ID from the provided JSON token.
   * The method decodes the token using the configured JWT algorithm and then extracts
   * the 'user' claim as an integer value.
   *
   * @param json A JsonNode containing the JWT token to be decrypted. The token should
   *        be provided in the 'token' field of the JsonNode.
   * @return The user ID extracted from the 'user' claim of the token.
   * @throws JWTVerificationException if the token cannot be verified, or if the 'user'
   *         claim is missing or not an integer.
   */
  public int getIdFromJsonToken(JsonNode json) throws JWTVerificationException {
    String jsonToken = json.get("token").asText(); // Extract the token as text
    DecodedJWT jwt = JWT.require(jwtAlgorithm) // Require and decode the token
        .withIssuer("auth0") // Specify the expected issuer
        .build()
        .verify(jsonToken);
    int userId = jwt.getClaim("user").asInt(); // Extract the user ID claim
    if (userId == -1) { // Check if the user ID is -1 (indicating missing)
      throw new JWTVerificationException(
          "User ID claim is missing"); // Throw an exception if missing
    }
    return userId; // Return the extracted user ID
  }
}





