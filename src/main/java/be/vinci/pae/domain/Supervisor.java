package be.vinci.pae.domain;

public interface Supervisor {

  int getResponsibleId();

  void setResponsibleId(int responsibleId);

  String getName();

  void setName(String name);

  String getSurname();

  void setSurname(String surname);

  String getPhone();

  void setPhone(String phone);

  String getEmail();

  void setEmail(String email);

  int getEnterprise();

  void setEnterprise(int enterprise);

  Enterprise getEnterpriseDTO();

  void setEnterpriseDTO(Enterprise enterpriseDTO);
}
