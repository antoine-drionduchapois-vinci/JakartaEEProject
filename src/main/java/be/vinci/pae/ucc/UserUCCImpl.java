package be.vinci.pae.ucc;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import jakarta.inject.Inject;
import java.util.List;


/**
 * Implementation of the UserDataService interface.
 */

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO myUserDAO;

  @Override
  public int countStudentsWithoutStage() {

    int studentsWithoutInternship = myUserDAO.getStudentsWithoutStage();

    return studentsWithoutInternship;
  }

  @Override
  public int countStudents() {
    return myUserDAO.getTotalStudents();
  }


  @Override
  public List<User> getUsersAsJson() {

    // Récupérer la liste complète des utilisateurs depuis votre DAO
    List<User> userList = myUserDAO.getAllStudents();

    return userList;
  }

  @Override
  public User getUsersByIdAsJson(int userId) {

    User user = myUserDAO.getOneByID(userId);

    return user;
  }


}