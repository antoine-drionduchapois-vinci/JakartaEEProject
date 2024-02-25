package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing JDBC connections.
 */
public class JDBCManager {

  private Connection connection;

  /**
   * Constructs a new JDBCManager and establishes a connection to the database.
   *
   * @throws SQLException if a database access error occurs or the URL is null
   */
  public JDBCManager() throws SQLException {
    // Chargement des informations sensibles à partir du fichier dev.properties
    Config.load("dev.properties");

    // Récupération des informations de connexion à la base de données depuis Config
    String url = Config.getProperty("db.url");
    String username = Config.getProperty("db.username");
    String password = Config.getProperty("db.password");

    try {
      Class.forName("org.postgresql.Driver");
      this.connection = DriverManager.getConnection(url, username, password);
      System.out.println("Connected to dB: " + this.connection);
    } catch (ClassNotFoundException e) {
      System.out.println("PostgreSQL driver missing!");
      System.exit(1);
    } catch (SQLException e) {
      System.out.println("Unable to connect to the server!");
      System.exit(1);
    }
  }


  /**
   * Returns the connection to the database.
   *
   * @return the connection to the database
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Closes the database connection.
   *
   * @throws SQLException if a database access error occurs
   */
  public void close() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
      System.out.println("Connection closed");
    }
  }
}
