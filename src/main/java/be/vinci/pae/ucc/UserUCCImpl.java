package be.vinci.pae.ucc;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.dao.UserDAOImpl;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
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