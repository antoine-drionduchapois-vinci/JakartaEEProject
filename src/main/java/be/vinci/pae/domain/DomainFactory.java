package be.vinci.pae.domain;

/**
 * A factory interface for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Gets a new instance of User.
   *
   * @return A new instance of User.
   */
  User getUser();
}
