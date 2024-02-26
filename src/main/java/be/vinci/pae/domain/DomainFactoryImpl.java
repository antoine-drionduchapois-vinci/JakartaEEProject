package be.vinci.pae.domain;

/**
 * Implementation of the DomainFactory interface that provides methods for creating domain objects.
 */
public class DomainFactoryImpl implements DomainFactory {

  /**
   * Creates and returns a new instance of User.
   *
   * @return A new instance of User.
   */
  @Override
  public User getUser() {
    return new UserImpl();
  }
}
