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
    try {
      myDALService.start();

      int studentsWithoutInternship = myUserDAO.getStudentsWithoutStage();
      myDALService.commit();

      return studentsWithoutInternship;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public int countStudents() {
    try {
      myDALService.start();
      int t = myUserDAO.getTotalStudents();
      myDALService.commit();
      return t;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }


  @Override
  public List<UserDTO> getUsersAsJson() {
    try {
      myDALService.start();
      // Récupérer la liste complète des utilisateurs depuis votre DAO
      List<UserDTO> userList = myUserDAO.getAllStudents();
      myDALService.commit();

      return userList;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public UserDTO getUsersByIdAsJson(int userId) {
    try {
      myDALService.start();
      UserDTO userDTO = myUserDAO.getOneByID(userId);
      if (userDTO == null) {
        throw new NotFoundException();
      }
      myDALService.commit();

      return userDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public UserDTO modifyPassword(UserDTO userDTO, String newMdp) {
    try {
      myDALService.start();

      userDTO.setPassword(newMdp);
      User user = (User) userDTO;
      user.hashPassword(userDTO.getPassword());

      UserDTO userDTO1 = myUserDAO.modifyPassword(user);

      myDALService.commit();

      return userDTO1;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }


  @Override
  public UserDTO changePhoneNumber(UserDTO userDTO) {
    try {
      myDALService.start();

      UserDTO userDTO1 = myUserDAO.changePhoneNumber(userDTO);

      myDALService.commit();
      return userDTO1;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

}