package be.vinci.pae.domain;

public interface Internship {

  int getInternshipId();

  int getResponsible();

  String getSubject();

  int getEnterprise();

  int getContact();

  int getUser();

  String getYear();

  void setInternshipDTO(Internship InternshipDTO);
}
