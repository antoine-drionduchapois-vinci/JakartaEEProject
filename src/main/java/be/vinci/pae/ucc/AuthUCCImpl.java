package be.vinci.pae.ucc;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.DALService;
import jakarta.inject.Inject;

/**
 * Implementation of the EnterpriseUCC interface.
 */
public class AuthUCCImpl implements AuthUCC {


  @Inject
  private UserDAO myUserDAO;

  @Inject
  private DALService myDALService;

  @Override
  public UserDTO login(UserDTO userTemp) {
    myDALService.start();

    UserDTO userDTO = myUserDAO.getOneByEmail(userTemp.getEmail());

    myDALService.commit();
    User user = (User) userDTO;
    if (user == null || !user.checkPassword(userTemp.getPassword())) {
      return null;
    }
    return userDTO;

  }

  @Override
  public UserDTO register(UserDTO userTemp) {
    myDALService.start();
    //todo
    // User tempUser = (User) myUserDAO.getOneByEmail(email);
    //    if (tempUser != null) {
    //      return null; // User already exists!
    //    }
    UserDTO user = myUserDAO.addUser(userTemp);
    //faire dto dans ressource cast et check role
    myDALService.commit();
    if (user == null) {
      return null;
    }
    return user;
  }


}
