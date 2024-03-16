package be.vinci.pae.domain;

/**
 * The DomainFactory interface provides methods for creating domain objects.
 */
public interface DomainFactory {

  /**
   * Creates a new instance of UserDTO.
   *
   * @return A new UserDTO object.
   */
  UserDTO getUserDTO();

  /**
   * Creates a new instance of ResponsibleDTO with the specified parameters.
   *
   * @param responsibleId The ID of the responsible.
   * @param name          The name of the responsible.
   * @param surname       The surname of the responsible.
   * @param phone         The phone number of the responsible.
   * @param email         The email address of the responsible.
   * @param enterprise    The ID of the enterprise associated with the responsible.
   * @return A new ResponsibleDTO object initialized with the provided parameters.
   */
  ResponsibleDTO getResponsibleDTO(int responsibleId, String name, String surname, String phone,
      String email, int enterprise);

  EnterpriseDTO getEnterpriseDTO();

  /**
   * Creates a new instance of EnterpriseDTO with the specified parameters.
   *
   * @param entrepriseId   The ID of the enterprise.
   * @param nom            The name of the enterprise.
   * @param appellation    The appellation of the enterprise.
   * @param adresse        The address of the enterprise.
   * @param telephone      The phone number of the enterprise.
   * @param isBlacklist    The blacklist status of the enterprise.
   * @param avisProfesseur The professor's opinion about the enterprise.
   * @return A new EnterpriseDTO object initialized with the provided parameters.
   */
  EnterpriseDTO getEnterpriseDTO(int entrepriseId, String nom, String appellation,
      String adresse, String telephone, boolean isBlacklist,
      String avisProfesseur);

  /**
   * Creates a new instance of ContactDTO.
   *
   * @return A new ContactDTO object.
   */
  ContactDTO getContactDTO();

  /**
   * Creates a new instance of ContactDTO with the specified parameters.
   *
   * @param contactId     The ID of the contact.
   * @param description   The description of the contact.
   * @param state         The state of the contact.
   * @param reasonRefusal The reason for refusal of the contact.
   * @param year          The year of the contact.
   * @param user          The ID of the user associated with the contact.
   * @param entreprise    The ID of the enterprise associated with the contact.
   * @return A new ContactDTO object initialized with the provided parameters.
   */
  ContactDTO getContactDTO(int contactId, String description, String state, String reasonRefusal,
      String year, int user, int entreprise);
}
