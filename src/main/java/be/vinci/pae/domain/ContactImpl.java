package be.vinci.pae.domain;

/**
 * Implementation of the Contact interface representing a contact entity.
 */
public class ContactImpl implements ContactDTO {

  private int contactId;
  private String description;
  private String state;
  private String reasonRefusal;
  private String year;
  private int user;
  private int entreprise;

  public ContactImpl() {
  }

  /**
   * Constructs a new ContactImpl object with the specified attributes.
   *
   * @param contactId     The ID of the contact.
   * @param description   The description of the contact.
   * @param state         The state of the contact.
   * @param reasonRefusal The reason for refusal of the contact.
   * @param year          The year of the contact.
   * @param user          The ID of the user associated with the contact.
   * @param entreprise    The ID of the enterprise associated with the contact.
   */
  public ContactImpl(int contactId, String description, String state, String reasonRefusal,
      String year, int user, int entreprise) {
    this.contactId = contactId;
    this.description = description;
    this.state = state;
    this.reasonRefusal = reasonRefusal;
    this.year = year;
    this.user = user;
    this.entreprise = entreprise;
  }

  @Override
  public int getContactId() {
    return contactId;
  }

  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getReasonRefusal() {
    return reasonRefusal;
  }

  public void setReasonRefusal(String reasonRefusal) {
    this.reasonRefusal = reasonRefusal;
  }

  @Override
  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  @Override
  public int getEntreprise() {
    return entreprise;
  }

  public void setEntreprise(int entreprise) {
    this.entreprise = entreprise;
  }
}
