package be.vinci.pae.domain;

/**
 * Interface representing an enterprise entity.
 */
public interface EnterpriseDTO {

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

  /**
   * Sets the ID of the enterprise.
   *
   * @param entrepriseId The ID of the enterprise to set.
   */
  void setEntrepriseId(int entrepriseId);

  /**
   * Sets the name of the enterprise.
   *
   * @param nom The name of the enterprise to set.
   */
  void setNom(String nom);

  /**
   * Sets the appellation of the enterprise.
   *
   * @param appellation The appellation of the enterprise to set.
   */
  void setAppellation(String appellation);

  /**
   * Sets the address of the enterprise.
   *
   * @param adresse The address of the enterprise to set.
   */
  void setAdresse(String adresse);

  /**
   * Sets the telephone number of the enterprise.
   *
   * @param telephone The telephone number of the enterprise to set.
   */
  void setTelephone(String telephone);

  /**
   * Sets whether the enterprise is blacklisted.
   *
   * @param blacklist True if the enterprise is blacklisted, false otherwise.
   */
  void setBlacklist(boolean blacklist);

  /**
   * Sets the professor's opinion about the enterprise.
   *
   * @param avisProfesseur The professor's opinion about the enterprise to set.
   */
  void setAvisProfesseur(String avisProfesseur);
}
