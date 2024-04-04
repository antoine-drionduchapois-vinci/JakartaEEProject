package be.vinci.pae.domain;

/**
 * Implementation of the Enterprise interface.
 */
public class SupervisorImpl implements Supervisor {

  private int supervisorId;
  private String name;
  private String surname;
  private String phone;
  private String email;
  private int enterprise;
  private Enterprise enterpriseDTO;
  private int version;

  @Override
  public int getResponsibleId() {
    return supervisorId;
  }

  @Override
  public void setResponsibleId(int responsibleId) {
    this.supervisorId = responsibleId;
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
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "SupervisorImpl{" +
        "supervisorId=" + supervisorId +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", enterprise=" + enterprise +
        ", enterpriseDTO=" + enterpriseDTO +
        ", numVersion=" + version +
        '}';
  }
}
