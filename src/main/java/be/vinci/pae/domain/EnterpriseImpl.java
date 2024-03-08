package be.vinci.pae.domain;

public class EnterpriseImpl implements be.vinci.pae.domain.Enterprise {
  private int entrepriseId;
  private String nom;
  private String appellation;
  private String adresse;
  private String telephone;
  private boolean isBlacklist;
  private String avisProfesseur;

  // Constructeur
  public EnterpriseImpl(int entrepriseId, String nom, String appellation, String adresse, String telephone, boolean isBlacklist, String avisProfesseur) {
    this.entrepriseId = entrepriseId;
    this.nom = nom;
    this.appellation = appellation;
    this.adresse = adresse;
    this.telephone = telephone;
    this.isBlacklist = isBlacklist;
    this.avisProfesseur = avisProfesseur;
  }

  // Getters
  @Override public int getEntrepriseId() {
    return entrepriseId;
  }

  @Override public String getNom() {
    return nom;
  }

  @Override public String getAppellation() {
    return appellation;
  }

  @Override public String getAdresse() {
    return adresse;
  }

  @Override public String getTelephone() {
    return telephone;
  }

  @Override public boolean isBlacklist() {
    return isBlacklist;
  }

  @Override public String getAvisProfesseur() {
    return avisProfesseur;
  }
}
