package be.vinci.pae.domain;

public interface DomainFactory {

  Contact getContact();

  Enterprise getEnterprise();

  Supervisor getSupervisor();

  User getUser();
}
