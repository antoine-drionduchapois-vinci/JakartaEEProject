package be.vinci.pae.utils;

import be.vinci.pae.services.UserDataService;
import be.vinci.pae.services.UserDataServiceImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UserDataServiceImpl.class).to(UserDataService.class).in(Singleton.class);
    }
}