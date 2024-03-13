package be.vinci.pae.domain;

/**
 * Implementation of the Responsible interface representing an responsible entity.
 */
public class ResponsibleImpl implements Responsible {

  private int responsibleId;
  private String name;
  private String surname;
  private String phone;
  private String email;
  private int enterprise;

  /**
   * Constructs a new ResponsibleImpl object with the specified attributes.
   *
   * @param responsibleId The unique identifier for the responsible.
   * @param name          The name of the responsible.
   * @param surname       The surname of the responsible.
   * @param phone         The phone number of the responsible.
   * @param email         The email address of the responsible.
   * @param enterprise    The ID of the enterprise associated with the responsible.
   */

  public ResponsibleImpl(int responsibleId, String name, String surname, String phone, String email,
      int enterprise) {
    this.responsibleId = responsibleId;
    this.name = name;
    this.surname = surname;
    this.phone = phone;
    this.email = email;
    this.enterprise = enterprise;
  }


  @Override
  public int getEnterprise() {
    return enterprise;
  }


  @Override
  public int getResponsibleId() {
    return responsibleId;
  }


  @Override
  public String getEmail() {
    return email;
  }


  @Override
  public String getName() {
    return name;
  }


  @Override
  public String getPhone() {
    return phone;
  }


  @Override
  public String getSurname() {
    return surname;
  }
}
