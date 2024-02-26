package be.vinci.pae.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for handling application configuration properties.
 */
public class Config {

  private static Properties props;

  /**
   * Loads properties from a file into the 'props' object.
   *
   * @param file The path to the properties file to be loaded.
   */
  public static void load(String file) {
    props = new Properties();
    try (InputStream input = new FileInputStream(file)) {
      props.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves the value of the property associated with the specified key.
   *
   * @param key The key of the property.
   * @return The value of the property as a string, or null if the property is not found.
   */
  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  /**
   * Retrieves the value of the property associated with the specified key as an integer.
   *
   * @param key The key of the property.
   * @return The value of the property as an integer, or null if the property is not found or cannot
   * be parsed as an integer.
   */
  public static Integer getIntProperty(String key) {
    return Integer.parseInt(props.getProperty(key));
  }

  /**
   * Retrieves the value of the property associated with the specified key as a boolean.
   *
   * @param key The key of the property.
   * @return The value of the property as a boolean, or false if the property is not found or cannot
   * be parsed as a boolean.
   */
  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(props.getProperty(key));
  }
}
