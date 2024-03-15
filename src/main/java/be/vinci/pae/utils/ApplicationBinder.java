package be.vinci.pae.utils;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.services.EnterpriseUCC;
import be.vinci.pae.services.EnterpriseUCCImpl;
import be.vinci.pae.services.UserUCC;
import be.vinci.pae.services.UserUCCImpl;
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
    bind(DALServiceImpl.class).to(DALService.class).in(Singleton.class);
  }
}