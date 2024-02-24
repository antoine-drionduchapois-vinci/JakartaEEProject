package be.vinci.pae.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager {

  private Connection connection;


  public JDBCManager() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbdiego_rousseaux";
    try {
      this.connection = DriverManager.getConnection(url, "diego_rousseaux", "Cristal55.");
      System.out.println("Connected to dB : " + this.connection);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }

  }

  public Connection getConnection() {
    return connection;
  }

  public void close() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
      System.out.println("Connection closed");
    }
  }
}
