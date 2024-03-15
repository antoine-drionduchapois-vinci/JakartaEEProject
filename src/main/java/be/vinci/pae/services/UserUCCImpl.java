package be.vinci.pae.services;

import be.vinci.pae.api.UserDAO;
import be.vinci.pae.api.UserDAOImpl;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.User.Role;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


/**
 * Implementation of the UserDataService interface.
 */

public class UserUCCImpl implements UserUCC {


  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  private UserDAO myUserDAO = new UserDAOImpl();


  @Override
  public int getGlobalStats() {

    int studentsWithoutInternship = myUserDAO.getStudentsWithoutStage();



    return studentsWithoutInternship;
  }


  @Override
  public List<UserDTO> getUsersAsJson() {

      // Récupérer la liste complète des utilisateurs depuis votre DAO
      List<UserDTO> userList = myUserDAO.getAllStudents();


    return userList;
  }

  @Override
  public UserDTO getUsersByIdAsJson(int userId) {


      UserDTO user = myUserDAO.getOneByID(userId);


    return user;
  }


}