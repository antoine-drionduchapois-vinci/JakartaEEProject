package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.NotFoundException;
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
  public List<UserDTO> getUsersAsJson() {
    myDALService.start();
    // Récupérer la liste complète des utilisateurs depuis votre DAO
    List<UserDTO> userList = myUserDAO.getAllStudents();
    myDALService.commit();

    return userList;
  }

  @Override
  public UserDTO getUsersByIdAsJson(int userId) {
    myDALService.start();
    UserDTO userDTO = myUserDAO.getOneByID(userId);
    if (userDTO == null) {
      throw new NotFoundException();
    }
    myDALService.commit();

    return userDTO;
  }

  public UserDTO modifyPassword(UserDTO userDTO, String newMdp) {
    myDALService.start();

    userDTO.setPassword(newMdp);
    User user = (User) userDTO;
    user.hashPassword(userDTO.getPassword());

    UserDTO userDTO1 = myUserDAO.modifyPassword(user);

    myDALService.commit();

    return userDTO1;
  }

}