package be.vinci.pae.utils;

/**
 * The DALService interface defines methods for managing database access.
 */
public interface DALService {

  /**
   * Initializes the data source for database connections.
   */
  void start();

  /**
   * Commits the transaction.
   **/
  void commit();
}
