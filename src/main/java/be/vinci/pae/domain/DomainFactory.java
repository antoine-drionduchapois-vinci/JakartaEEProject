package be.vinci.pae.domain;

/**
 * The DomainFactory interface provides methods for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Creates a new instance of Contact.
   *
   * @return A new Contact object.
   */
  ContactDTO getContact();

  /**
   * Creates a new instance of Enterprise.
   *
   * @return A new Enterprise object.
   */
  Enterprise getEnterprise();

  /**
   * Creates a new instance of Supervisor.
   *
   * @return A new Supervisor object.
   */
  Supervisor getSupervisor();

  /**
   * Creates a new instance of User.
   *
   * @return A new User object.
   */
  User getUser();

  /**
   * Creates a new instance of Internship.
   *
   * @return A new Internship object.
   */
  Internship getInternship();
}
