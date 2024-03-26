package be.vinci.pae;

import static org.mockito.Mockito.mock;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.ContactDAOImpl;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TestBinder extends AbstractBinder {

  @Override
  protected void configure() {
    DALServiceImpl dalServiceMock = mock(DALServiceImpl.class);
    bind(dalServiceMock).to(DALService.class).to(DALBackService.class);

    bind(DomainFactoryImpl.class).to(DomainFactory.class);
    
    bind(ContactDAOImpl.class).to(ContactDAO.class);
    bind(EnterpriseDAOImpl.class).to(EnterpriseDAO.class);
  }
}
