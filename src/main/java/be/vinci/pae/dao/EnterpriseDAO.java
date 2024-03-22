package be.vinci.pae.dao;

import be.vinci.pae.domain.Enterprise;
import java.util.List;

/**
 * The EnterpriseDAO interface provides methods for accessing enterprise
 * information from the database.
 */
public interface EnterpriseDAO {

  /**
   * Retrieves the enterprise information associated with the specified enterprise
   * ID.
   *
   * @param enterpriseId The ID of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information
   *         associated with the
   *         specified ID, or null if no enterprise with the given ID is found.
   */
  Enterprise readOne(int enterpriseId);

  Enterprise readOne(String enterpriseName, String enterpriseLabel);

  /**
   * Creates a new enterprise with the given name, label, address, and contact
   * information.
   *
   * @param name    The name of the enterprise.
   * @param label   The label of the enterprise.
   * @param address The address of the enterprise.
   * @param contact The contact information of the enterprise.
   * @return An EnterpriseDTO object representing the newly created enterprise.
   */
  Enterprise create(String name, String label, String address, String contact);

  /**
   * Retrieves a list of all enterprises stored in the database.
   *
   * @return A List containing EnterpriseDTO objects representing all enterprises
   *         stored in the database.
   */
  List<Enterprise> getAllEnterprises();

  /**
   * Retrieves the enterprise information associated with the specified ID.
   *
   * @param id The ID of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information
   *         associated with the specified ID,
   *         or null if no enterprise with the given ID is found.
   */
  Enterprise getEnterpriseById(int id);
}
