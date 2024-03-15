package be.vinci.pae.domain;

/**
 * Implementation of the Contact interface representing a contact entity.
 */
public class ContactImpl implements Contact {

  private int contactId;
  private String description;
  private String state;
  private String reasonRefusal;
  private String year;
  private int user;
  private int entreprise;

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

  /**
   * Gets the ID of the contact.
   *
   * @return The ID of the contact.
   */
  @Override
  public int getContactId() {
    return contactId;
  }

  /**
   * Gets the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  @Override
  public int getEntreprise() {
    return entreprise;
  }

  /**
   * Gets the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  @Override
  public int getUser() {
    return user;
  }

  /**
   * Gets the description of the contact.
   *
   * @return The description of the contact.
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Gets the reason for refusal of the contact.
   *
   * @return The reason for refusal of the contact.
   */
  @Override
  public String getReasonRefusal() {
    return reasonRefusal;
  }

  /**
   * Gets the state of the contact.
   *
   * @return The state of the contact.
   */
  @Override
  public String getState() {
    return state;
  }

  /**
   * Gets the year of the contact.
   *
   * @return The year of the contact.
   */
  @Override
  public String getYear() {
    return year;
  }
}
