package be.vinci.pae.domain;

public class SupervisorImpl implements Supervisor {

  private int responsibleId;
  private String name;
  private String surname;
  private String phone;
  private String email;
  private int enterprise;
  private Enterprise enterpriseDTO;

  @Override
  public int getResponsibleId() {
    return responsibleId;
  }

  @Override
  public void setResponsibleId(int responsibleId) {
    this.responsibleId = responsibleId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getSurname() {
    return surname;
  }

  @Override
  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int getEnterprise() {
    return enterprise;
  }

  @Override
  public void setEnterprise(int enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public Enterprise getEnterpriseDTO() {
    return enterpriseDTO;
  }

  @Override
  public void setEnterpriseDTO(Enterprise enterpriseDTO) {
    this.enterpriseDTO = enterpriseDTO;
  }

  @Override
  public String toString() {
    return "ResponsibleImpl{" +
        "responsibleId=" + responsibleId +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", enterprise=" + enterprise +
        ", enterpriseDTO=" + enterpriseDTO +
        '}';
  }
}
