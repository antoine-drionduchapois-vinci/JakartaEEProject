package be.vinci.pae.domain;

public interface DomainFactory {

  UserDTO getUserDTO();

  ResponsibleDTO getResponsibleDTO(int responsibleId, String name, String surname, String phone,
      String email, int enterprise);

  EnterpriseDTO getEnterpriseDTO(int entrepriseId, String nom, String appellation,
      String adresse, String telephone, boolean isBlacklist, String avisProfesseur);

  ContactDTO getContactDTO();

  ContactDTO getContactDTO(int contactId, String description, String state, String reasonRefusal,
      String year, int user, int entreprise);
}
