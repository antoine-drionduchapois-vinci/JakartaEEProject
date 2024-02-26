package be.vinci.pae.utils;

import be.vinci.pae.services.UserDataService;
import be.vinci.pae.services.UserDataServiceImpl;
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
    bind(UserDataServiceImpl.class).to(UserDataService.class).in(Singleton.class);
    bind(JDBCManagerImpl.class).to(JDBCManager.class).in(Singleton.class);
  }
}