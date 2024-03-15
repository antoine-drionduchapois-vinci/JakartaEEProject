package be.vinci.pae.domain;

public class DomainFactoryImpl implements DomainFactory {

  @Override
  public UserDTO getUserDTO() {
    return new UserImpl();
  }

  @Override
  public ResponsibleDTO getResponsibleDTO(int responsibleId, String name, String surname,
      String phone,
      String email, int enterprise) {
    return new ResponsibleImpl(responsibleId, name, surname, phone, email, enterprise);
  }

  @Override
  public EnterpriseDTO getEnterpriseDTO(int entrepriseId, String nom, String appellation,
      String adresse, String telephone, boolean isBlacklist, String avisProfesseur) {
    return new EnterpriseDTOImpl(entrepriseId, nom, appellation, adresse, telephone, isBlacklist,
        avisProfesseur);
  }

  @Override
  public ContactDTO getContactDTO() {
    return new ContactImpl();
  }

  @Override
  public ContactDTO getContactDTO(int contactId, String description, String state,
      String reasonRefusal,
      String year, int user, int entreprise) {
    return new ContactImpl(contactId, description, state, reasonRefusal, year, user, entreprise);
  }
}
