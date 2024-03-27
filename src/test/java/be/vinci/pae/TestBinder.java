package be.vinci.pae;

import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.EnterpriseUCCImpl;
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
    bind(Mockito.mock(EnterpriseDAOImpl.class)).to(EnterpriseDAO.class);
    bind(EnterpriseUCCImpl.class).to(EnterpriseUCC.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class);

    // Vous pouvez ajouter d'autres liaisons pour d'autres mocks si nécessaire
  }
}

