package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
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
    if (user == null) {
      throw new NotFoundException();
    }
    if (!user.checkPassword(userTemp.getPassword())) {
      throw new BusinessException(401, "Wrong password");
    }
    return userDTO;

  }

  @Override
  public UserDTO register(UserDTO userTemp) {
    myDALService.start();
    User tempUser = (User) myUserDAO.getOneByEmail(userTemp.getEmail());
    if (tempUser != null) {
      throw new BusinessException(409, "User already exists!");
    }
    User user = (User) userTemp;
    user.hashPassword(userTemp.getPassword());
    UserDTO userDTO = myUserDAO.addUser(user);
    //faire dto dans ressource cast et check role
    myDALService.commit();
    return userDTO;
  }
  
}
