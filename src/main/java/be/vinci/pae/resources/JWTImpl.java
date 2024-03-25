package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JWTImpl implements JWT {
  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Override
  public ObjectNode createToken(UserDTO userDTO){
    String token;
    try {
      token = com.auth0.jwt.JWT.create().withIssuer("auth0")
          .withClaim("user", userDTO.getUserId()).sign(this.jwtAlgorithm);
      return jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", userDTO.getUserId())
          .put("email", userDTO.getEmail())
          .put("name", userDTO.getName())
          .put("telephone", userDTO.getPhone())
          .put("annee", userDTO.getYear())
          .put("role", userDTO.getRole().name());
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

}
