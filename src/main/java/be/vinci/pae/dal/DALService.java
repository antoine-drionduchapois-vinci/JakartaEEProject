package be.vinci.pae.dal;

/**
 * The DALService interface defines methods for starting and commit database access.
 */
public interface DALService {

  /**
   * Initializes the data source for database connections and increments the transaction counter.
   */
  void start();

  /**
   * Commits the transaction and close the connection (When the counter transaction = 0). If there
   * is an error, make a rollback of the transaction.
   **/
  void commit();

  /**
   * make a rollback of the transaction.
   **/
  void rollback();

}
