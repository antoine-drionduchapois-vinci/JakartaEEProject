package be.vinci.pae.ucc;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.utils.DALService;
import jakarta.inject.Inject;
import java.util.List;


/**
 * Implementation of the UserDataService interface.
 */

public class UserUCCImpl implements UserUCC {

  @Inject
  private UserDAO myUserDAO;

  @Inject
  private DALService myDALService;

  @Override
  public int countStudentsWithoutStage() {
    myDALService.start();

    int studentsWithoutInternship = myUserDAO.getStudentsWithoutStage();
    myDALService.commit();

    return studentsWithoutInternship;
  }

  @Override
  public int countStudents() {
    myDALService.start();
    int t = myUserDAO.getTotalStudents();
    myDALService.commit();
    return t;
  }


  @Override
  public List<User> getUsersAsJson() {
    myDALService.start();
    // Récupérer la liste complète des utilisateurs depuis votre DAO
    List<User> userList = myUserDAO.getAllStudents();
    myDALService.commit();

    return userList;
  }

  @Override
  public User getUsersByIdAsJson(int userId) {
    myDALService.start();
    User user = myUserDAO.getOneByID(userId);
    myDALService.commit();

    return user;
  }


}