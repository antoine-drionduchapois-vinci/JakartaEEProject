package be.vinci.pae.utils;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.ContactDAOImpl;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.dao.ResponsibleDAO;
import be.vinci.pae.dao.ResponsibleDAOImpl;
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
import be.vinci.pae.ucc.ResponsibleUCC;
import be.vinci.pae.ucc.ResponsibleUCCImpl;
import be.vinci.pae.ucc.UserUCC;
import be.vinci.pae.ucc.UserUCCImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


/**
 * Provider class responsible for binding implementations to interfaces.
 */
@Provider
public class ApplicationBinder extends AbstractBinder {

  /**
   * Configures the bindings between implementations and interfaces.
   */
  @Override
  protected void configure() {
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(ResponsibleUCCImpl.class).to(ResponsibleUCC.class).in(Singleton.class);
    bind(EnterpriseUCCImpl.class).to(EnterpriseUCC.class).in(Singleton.class);
    bind(AuthUCCImpl.class).to(AuthUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(EnterpriseDAOImpl.class).to(EnterpriseDAO.class).in(Singleton.class);
    bind(ResponsibleDAOImpl.class).to(ResponsibleDAO.class).in(Singleton.class);
    bind(DALServiceImpl.class).to(DALService.class).in(Singleton.class);
  }
}