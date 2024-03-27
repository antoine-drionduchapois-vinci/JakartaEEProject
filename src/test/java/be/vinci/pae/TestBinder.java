package be.vinci.pae;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.ContactDAOImpl;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.dao.SupervisorDAOImpl;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.dao.UserDAOImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.ucc.AuthUCCImpl;
import be.vinci.pae.ucc.ContactUCC;
import be.vinci.pae.ucc.ContactUCCImpl;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.EnterpriseUCCImpl;
import be.vinci.pae.ucc.SupervisorUCC;
import be.vinci.pae.ucc.SupervisorUCCImpl;
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
    bind(DomainFactoryImpl.class).to(DomainFactory.class);

    bind(Mockito.mock(EnterpriseDAOImpl.class)).to(EnterpriseDAO.class);
    bind(Mockito.mock(UserDAOImpl.class)).to(UserDAO.class);
    bind(Mockito.mock(ContactDAOImpl.class)).to(ContactDAO.class);
    bind(Mockito.mock(SupervisorDAOImpl.class)).to(SupervisorDAO.class);

    bind(UserUCCImpl.class).to(UserUCC.class);
    bind(AuthUCCImpl.class).to(AuthUCC.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class);
    bind(EnterpriseUCCImpl.class).to(EnterpriseUCC.class);
    bind(SupervisorUCCImpl.class).to(SupervisorUCC.class);
  }
}