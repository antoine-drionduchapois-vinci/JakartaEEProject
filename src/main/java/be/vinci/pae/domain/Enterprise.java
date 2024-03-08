package be.vinci.pae.domain;

/**
 * Interface representing an enterprise entity.
 */
public interface Enterprise {

  /**
   * Gets the ID of the enterprise.
   *
   * @return The ID of the enterprise.
   */
  int getEntrepriseId();

  /**
   * Gets the name of the enterprise.
   *
   * @return The name of the enterprise.
   */
  String getNom();

  /**
   * Gets the appellation of the enterprise.
   *
   * @return The appellation of the enterprise.
   */
  String getAppellation();

  /**
   * Gets the address of the enterprise.
   *
   * @return The address of the enterprise.
   */
  String getAdresse();

  /**
   * Gets the telephone number of the enterprise.
   *
   * @return The telephone number of the enterprise.
   */
  String getTelephone();

  /**
   * Checks if the enterprise is blacklisted.
   *
   * @return True if the enterprise is blacklisted, false otherwise.
   */
  boolean isBlacklist();

  /**
   * Gets the professor's opinion about the enterprise.
   *
   * @return The professor's opinion about the enterprise.
   */
  String getAvisProfesseur();
}
