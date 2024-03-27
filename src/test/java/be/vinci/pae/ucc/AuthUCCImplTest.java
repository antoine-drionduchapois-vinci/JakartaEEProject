package be.vinci.pae.ucc;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.utils.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;

class AuthUCCImplTest {

  private AuthUCC authUCC;
  private UserDAO userDAO;
  private ObjectMapper objectMapper = new ObjectMapper();

  static {
    Config.load("dev.properties");
  }

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.userDAO = locator.getService(UserDAO.class);
    this.authUCC = locator.getService(AuthUCC.class);
  }
}