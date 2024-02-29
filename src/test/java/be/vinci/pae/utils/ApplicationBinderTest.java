package be.vinci.pae.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.services.UserDataService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationBinderTest {

  static {
    Config.load("dev.properties");
  }
  private ServiceLocator serviceLocator;

  @BeforeEach
  void setUp() {
    serviceLocator = ServiceLocatorUtilities.bind(new ApplicationBinder());
  }

  @Test
  void testBindingDomainFactory() {
    DomainFactory domainFactory = serviceLocator.getService(DomainFactory.class);
    assertNotNull(domainFactory, "DomainFactory should be bound");
  }

  @Test
  void testBindingUserDataService() {
    UserDataService userDataService = serviceLocator.getService(UserDataService.class);
    assertNotNull(userDataService, "UserDataService should be bound");
  }

  @Test
  void testBindingJDBCManager() {
    JDBCManager jdbcManager = serviceLocator.getService(JDBCManager.class);
    assertNotNull(jdbcManager, "JDBCManager should be bound");
  }
}
