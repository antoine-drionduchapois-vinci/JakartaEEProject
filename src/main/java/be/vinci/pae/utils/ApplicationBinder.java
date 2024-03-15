package be.vinci.pae.utils;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.ucc.AuthUCCImpl;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.EnterpriseUCCImpl;
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
    bind(UserImpl.class).to(UserDTO.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(EnterpriseUCCImpl.class).to(EnterpriseUCC.class).in(Singleton.class);
    bind(AuthUCCImpl.class).to(AuthUCC.class).in(Singleton.class);
    bind(DALServiceImpl.class).to(DALService.class).in(Singleton.class);
  }
}