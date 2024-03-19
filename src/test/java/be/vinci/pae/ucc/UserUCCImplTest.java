package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.utils.Config;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserUCCImplTest {

  private UserUCC userUCC;
  private UserDAO userDAO;

  static {
    Config.load("dev.properties");
  }

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.userDAO = locator.getService(UserDAO.class);
    this.userUCC = locator.getService(UserUCC.class);
  }

  @Test
  void countStudentsWithoutStage() {
    when(userDAO.getStudentsWithoutStage()).thenReturn(5);

    int result = userUCC.countStudentsWithoutStage();

    assertEquals(5, result);
    verify(userDAO, times(1)).getStudentsWithoutStage();
  }
}