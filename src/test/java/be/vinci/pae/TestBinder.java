package be.vinci.pae;

import static org.mockito.Mockito.mock;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.dao.UserDAOImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.ucc.AuthUCCImpl;
import be.vinci.pae.ucc.UserUCC;
import be.vinci.pae.ucc.UserUCCImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

/**
 * Binder used for configuring dependency injection in tests.
 */
public class TestBinder extends AbstractBinder {

  /**
   * Configures the binder for dependency injection in tests.
   */
  @Override
  protected void configure() {
    bind(Mockito.mock(DALServiceImpl.class)).to(DALService.class).to(DALBackService.class);
    bind(mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(AuthUCCImpl.class).to(AuthUCC.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class);

  }
}