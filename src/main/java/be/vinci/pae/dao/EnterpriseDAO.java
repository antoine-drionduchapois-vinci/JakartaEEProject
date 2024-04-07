package be.vinci.pae.dao;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * The EnterpriseDAO interface provides methods for accessing enterprise information from the
 * database.
 */
public interface EnterpriseDAO {

  /**
   * Retrieves the enterprise information associated with the specified enterprise ID.
   *
   * @param enterpriseId The ID of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information associated with the
   * specified ID, or null if no enterprise with the given ID is found.
   */
  EnterpriseDTO readOne(int enterpriseId);

  /**
   * Retrieves the enterprise information associated with the specified enterprise name and
   * enterprise label.
   *
   * @param enterpriseName  The name of the enterprise to retrieve information for.
   * @param enterpriseLabel The label of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information associated with the
   * specified ID, or null if no enterprise with the given ID is found.
   */
  EnterpriseDTO readOne(String enterpriseName, String enterpriseLabel);

  /**
   * Creates a new Enterprise object with the specified attributes.
   *
   * @param name    The name of the enterprise.
   * @param label   The label of the enterprise.
   * @param address The address of the enterprise.
   * @param phone   The phone number of the enterprise.
   * @param email   The email address of the enterprise.
   * @return A new Enterprise object with the specified attributes.
   */
  EnterpriseDTO create(String name, String label, String address, String phone, String email);

  /**
   * Retrieves a list of all enterprises stored in the database.
   *
   * @return A List containing EnterpriseDTO objects representing all enterprises stored in the
   * database.
   */
  List<EnterpriseDTO> getAllEnterprises();

  /**
   * Retrieves the enterprise information associated with the specified ID.
   *
   * @param id The ID of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information associated with the
   * specified ID, or null if no enterprise with the given ID is found.
   */
  EnterpriseDTO getEnterpriseById(int id);

  /**
   * Blacklist an enterprise
   *
   * @param blacklistedEnterpriseDTO the EnterpriseDTO of the enterprise to blacklist
   * @return The enterprise blacklisted
   */
  EnterpriseDTO toBlacklist(EnterpriseDTO blacklistedEnterpriseDTO);
}
