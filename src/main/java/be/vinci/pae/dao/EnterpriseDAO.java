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

  /**
   * Retrieves the enterprise information associated with the specified enterprise
   * name and enterprise label.
   *
   * @param enterpriseName  The name of the enterprise to retrieve information
   *                        for.
   * @param enterpriseLabel The label of the enterprise to retrieve information
   *                        for.
   * @return An EnterpriseDTO object representing the enterprise information
   *         associated with the
   *         specified ID, or null if no enterprise with the given ID is found.
   */
  Enterprise readOne(String enterpriseName, String enterpriseLabel);

  Enterprise create(String name, String label, String adress, String phone, String email);

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
