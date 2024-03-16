package be.vinci.pae.domain;

/**
 * Implementation of the Enterprise interface representing an enterprise entity.
 */
public class EnterpriseDTOImpl implements EnterpriseDTO {

  private int entrepriseId;
  private String nom;
  private String appellation;
  private String adresse;
  private String telephone;
  private boolean isBlacklist;
  private String avisProfesseur;

  public EnterpriseDTOImpl() {
  }

  /**
   * Constructs a new EnterpriseImpl object with the specified attributes.
   *
   * @param entrepriseId   The ID of the enterprise.
   * @param nom            The name of the enterprise.
   * @param appellation    The appellation of the enterprise.
   * @param adresse        The address of the enterprise.
   * @param telephone      The telephone number of the enterprise.
   * @param isBlacklist    The blacklist status of the enterprise.
   * @param avisProfesseur The professor's opinion about the enterprise.
   */
  public EnterpriseDTOImpl(int entrepriseId, String nom, String appellation,
      String adresse, String telephone, boolean isBlacklist, String avisProfesseur) {
    this.entrepriseId = entrepriseId;
    this.nom = nom;
    this.appellation = appellation;
    this.adresse = adresse;
    this.telephone = telephone;
    this.isBlacklist = isBlacklist;
    this.avisProfesseur = avisProfesseur;
  }

  /**
   * Gets the ID of the enterprise.
   *
   * @return The ID of the enterprise.
   */
  @Override
  public int getEntrepriseId() {
    return entrepriseId;
  }

  /**
   * Gets the name of the enterprise.
   *
   * @return The name of the enterprise.
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Gets the appellation of the enterprise.
   *
   * @return The appellation of the enterprise.
   */
  @Override
  public String getAppellation() {
    return appellation;
  }

  /**
   * Gets the address of the enterprise.
   *
   * @return The address of the enterprise.
   */
  @Override
  public String getAdresse() {
    return adresse;
  }

  /**
   * Gets the telephone number of the enterprise.
   *
   * @return The telephone number of the enterprise.
   */
  @Override
  public String getTelephone() {
    return telephone;
  }

  /**
   * Checks if the enterprise is blacklisted.
   *
   * @return True if the enterprise is blacklisted, false otherwise.
   */
  @Override
  public boolean isBlacklist() {
    return isBlacklist;
  }

  /**
   * Gets the professor's opinion about the enterprise.
   *
   * @return The professor's opinion about the enterprise.
   */
  @Override
  public String getAvisProfesseur() {
    return avisProfesseur;
  }

  @Override
  public void setEntrepriseId(int entrepriseId) {
    this.entrepriseId = entrepriseId;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public void setAppellation(String appellation) {
    this.appellation = appellation;
  }

  @Override
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public void setBlacklist(boolean blacklist) {
    isBlacklist = blacklist;
  }

  @Override
  public void setAvisProfesseur(String avisProfesseur) {
    this.avisProfesseur = avisProfesseur;
  }
}
