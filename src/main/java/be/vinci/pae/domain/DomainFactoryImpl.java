package be.vinci.pae.domain;

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
