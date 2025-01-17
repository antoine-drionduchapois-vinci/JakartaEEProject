package be.vinci.pae.utils;

import be.vinci.pae.domain.UserDTO.Role;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Convert ResultSet to correspondant DTO.
 */
public class ResultSetMapper<T, U> {

  /**
   * Maps the data from a ResultSet to an object of type T using reflection.
   *
   * @param rs              The ResultSet containing the data to be mapped.
   * @param clazz           The Class object representing the type of object to be
   *                        created.
   * @param factoryFunction A supplier function that creates a new instance of
   *                        type T.
   * @return An object of type T mapped from the ResultSet, or null if no data is
   *         found in the
   *         ResultSet.
   * @throws SQLException           If a database access error occurs.
   * @throws IllegalAccessException If the class or its nullary constructor is not
   *                                accessible.
   */
  public T mapResultSetToObject(ResultSet rs, Class<U> clazz, Supplier<T> factoryFunction)
      throws SQLException, IllegalAccessException {
    try (rs) {
      if (!rs.next()) {
        return null;
      }
      Field[] fields = clazz.getDeclaredFields();
      return mapFields(fields, rs, factoryFunction);
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
  }

  /**
   * Maps the data from a ResultSet to a list of objects of type T using
   * reflection.
   *
   * @param rs              The ResultSet containing the data to be mapped.
   * @param clazz           The Class object representing the type of object to be
   *                        created.
   * @param factoryFunction A supplier function that creates a new instance of
   *                        type T.
   * @return A list of objects of type T mapped from the ResultSet.
   * @throws SQLException           If a database access error occurs.
   * @throws IllegalAccessException If the class or its nullary constructor is not
   *                                accessible.
   */
  public List<T> mapResultSetToObjectList(ResultSet rs, Class<U> clazz, Supplier<T> factoryFunction)
      throws SQLException, IllegalAccessException {
    List<T> objects = new ArrayList<>();

    try (rs) {
      if (!rs.next()) {
        return null;
      }
      Field[] fields = clazz.getDeclaredFields();
      objects.add(mapFields(fields, rs, factoryFunction));
      while (rs.next()) {
        objects.add(mapFields(fields, rs, factoryFunction));
      }
      return objects;
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
  }

  /**
   * Converts a camelCase string to snake_case.
   *
   * @param str The string to convert.
   * @return The string converted to snake_case.
   */
  private static String camelToSnakeCase(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    StringBuilder result = new StringBuilder();
    result.append(Character.toLowerCase(str.charAt(0)));
    for (int i = 1; i < str.length(); i++) {
      char currentChar = str.charAt(i);
      if (Character.isUpperCase(currentChar)) {
        result.append('_').append(Character.toLowerCase(currentChar));
      } else {
        result.append(currentChar);
      }
    }
    return result.toString();
  }

  private T mapFields(Field[] fields, ResultSet rs, Supplier<T> factoryFunction)
      throws SQLException, IllegalAccessException {
    T object = factoryFunction.get();
    for (Field field : fields) {
      if (field.getType() != String.class && field.getType() != int.class
          && field.getType() != boolean.class && field.getType() != Role.class) {
        continue;
      }
      field.setAccessible(true);
      String columnName = camelToSnakeCase(field.getName());
      if (rs.findColumn(columnName) != 0) {
        Object value = rs.getObject(columnName);
        if (field.getType() == Role.class) {
          value = Role.valueOf((String) value);
        }
        if (field.getType() == boolean.class && value == null) value = false;
        field.set(object, value);
      }
    }
    return object;
  }
}
