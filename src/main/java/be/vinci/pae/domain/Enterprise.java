package be.vinci.pae.domain;

public interface Enterprise {

  // Getters
  int getEntrepriseId();

  String getNom();

  String getAppellation();

  String getAdresse();

  String getTelephone();

  boolean isBlacklist();

  String getAvisProfesseur();
}
