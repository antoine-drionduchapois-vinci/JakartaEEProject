package be.vinci.pae.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResultSetMapper<T, U> {

  public T mapResultSetToObject(ResultSet rs, Class<U> clazz, Supplier<T> factoryFunction) throws SQLException, IllegalAccessException, InstantiationException {
    T object = factoryFunction.get();
    Field[] fields = clazz.getDeclaredFields();

    if (!rs.next()) return null;
    for (Field field : fields) {
      if (field.getType() != String.class && field.getType() != int.class) continue;
      field.setAccessible(true);
      String columnName = camelToSnakeCase(field.getName());
      if (rs.findColumn(columnName) != 0) {
        Object value = rs.getObject(columnName);
        field.set(object, value);
      }
    }
    return object;
  }

  public List<T> mapResultSetToObjectList(ResultSet rs, Class<U> clazz, Supplier<T> factoryFunction) throws SQLException, IllegalAccessException, InstantiationException {
    List<T> objects = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();

    while (rs.next()) {
      T object = factoryFunction.get();
      for (Field field : fields) {
        if (field.getType() != String.class && field.getType() != int.class) continue;
        field.setAccessible(true);
        String columnName = camelToSnakeCase(field.getName());
        if (rs.findColumn(columnName) != 0) {
          Object value = rs.getObject(columnName);
          field.set(object, value);
        }
      }
      objects.add(object);
    }
    return objects;
  }

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
}
