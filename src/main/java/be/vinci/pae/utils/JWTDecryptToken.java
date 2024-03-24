package be.vinci.pae.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;


public class JWTDecryptToken {


  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));


  public int getIdFromJsonToken(JsonNode json) throws JWTVerificationException {

    System.out.println("Received token: " + json); // Java
    String jsonToken = json.get("token").asText();
    System.out.println();
    DecodedJWT jwt = JWT.require(jwtAlgorithm)
        .withIssuer("auth0")
        .build()
        .verify(jsonToken);
    System.out.println(jwt);
    int userId = jwt.getClaim("user").asInt();
    System.out.println(userId);
    if (userId == -1) {
      throw new JWTVerificationException("User ID claim is missing");
    }
    return userId;
  }

}




