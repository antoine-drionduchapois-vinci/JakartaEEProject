package be.vinci.pae.utils;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.DALService;
import be.vinci.pae.dal.DALServiceImpl;
import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.ContactDAOImpl;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.dao.InternshipDAOImpl;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.dao.SupervisorDAOImpl;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.dao.UserDAOImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.DomainFactoryImpl;
import be.vinci.pae.resources.JWT;
import be.vinci.pae.resources.JWTImpl;
import be.vinci.pae.resources.filters.RoleId;
import be.vinci.pae.resources.filters.RoleIdImpl;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.ucc.AuthUCCImpl;
import be.vinci.pae.ucc.ContactUCC;
import be.vinci.pae.ucc.ContactUCCImpl;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.EnterpriseUCCImpl;
import be.vinci.pae.ucc.InternshipUCC;
import be.vinci.pae.ucc.InternshipUCCImpl;
import be.vinci.pae.ucc.SupervisorUCC;
import be.vinci.pae.ucc.SupervisorUCCImpl;
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
    bind(JWTImpl.class).to(JWT.class).in(Singleton.class);
    bind(RoleIdImpl.class).to(RoleId.class).in(Singleton.class);

    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(SupervisorUCCImpl.class).to(SupervisorUCC.class).in(Singleton.class);
    bind(EnterpriseUCCImpl.class).to(EnterpriseUCC.class).in(Singleton.class);
    bind(AuthUCCImpl.class).to(AuthUCC.class).in(Singleton.class);
    bind(ContactUCCImpl.class).to(ContactUCC.class).in(Singleton.class);
    bind(InternshipUCCImpl.class).to(InternshipUCC.class).in(Singleton.class);

    bind(UserDAOImpl.class).to(UserDAO.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(EnterpriseDAOImpl.class).to(EnterpriseDAO.class).in(Singleton.class);
    bind(ContactDAOImpl.class).to(ContactDAO.class).in(Singleton.class);
    bind(SupervisorDAOImpl.class).to(SupervisorDAO.class).in(Singleton.class);
    bind(InternshipDAOImpl.class).to(InternshipDAO.class).in(Singleton.class);

    bind(DALServiceImpl.class).to(DALService.class).to(DALBackService.class).in(Singleton.class);

  }
}