package be.vinci.pae.domain;

import jakarta.inject.Singleton;

/**
 * Implementation of the DomainFactory interface providing methods for creating domain objects.
 */
@Singleton
public class DomainFactoryImpl implements DomainFactory {

  @Override
  public Contact getContact() {
    return new ContactImpl();
  }

  @Override
  public Enterprise getEnterprise() {
    return new EnterpriseImpl();
  }

  @Override
  public Supervisor getSupervisor() {
    return new SupervisorImpl();
  }

  @Override
  public User getUser() {
    return new UserImpl();
  }
}
