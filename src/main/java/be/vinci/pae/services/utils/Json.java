package be.vinci.pae.services.utils;

import be.vinci.pae.utils.Config;
import be.vinci.pae.views.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JSON object with a generic type parameter. This class provides functionality to work
 * with JSON data.
 *
 * @param <T> the type of data stored in the JSON object
 */
public class Json<T> {

  private static final String DB_FILE_PATH = Config.getProperty("DatabaseFilePath");
  private static final ObjectMapper jsonMapper = new ObjectMapper();
  private static Path pathToDb = Paths.get(DB_FILE_PATH);
  private Class<T> type;

  // Java generics are mostly compile time, this means that the type information is lost at runtime.
  // To get the type information at runtime you have to add it as an argument of the constructor.
  public Json(Class<T> type) {
    this.type = type;
  }

  /**
   * Serializes a list of items into JSON and writes it to a file. If the file doesn't exist,
   * creates a new one. Updates the collection specified by collectionName.
   *
   * @param items          the list of items to serialize
   * @param collectionName the name of the collection to update or create
   */
  public void serialize(List<T> items, String collectionName) {
    try {
      // if no DB file, write a new collection to a new db file
      if (!Files.exists(pathToDb)) {
        // Create an object and add a JSON array as POJO, e.g. { items:[...]}
        ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(collectionName, items);
        jsonMapper.writeValue(pathToDb.toFile(),
            newCollection); // write the JSON Object in the DB file
        return;
      }
      // get all collections : can be read as generic JsonNode, if it can be Object or Array;
      JsonNode allCollections = jsonMapper.readTree(
          pathToDb.toFile()); // e.g. { users:[...], items:[...]}
      // remove current collection, e.g. remove the array of items
      if (allCollections.has(collectionName)) {
        ((ObjectNode) allCollections).remove(collectionName); //e.g. it leaves { users:[...]}
      }
      // Prepare a JSON array from the list of POJOs for the collection to be updated
      ArrayNode updatedCollection = jsonMapper.valueToTree(items);
      // Add the JSON array in allCollections, e.g. : { users:[...], items:[...]}
      ((ObjectNode) allCollections).putArray(collectionName).addAll(updatedCollection);
      // write to the db file allCollections
      jsonMapper.writeValue(pathToDb.toFile(), allCollections);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Parses the JSON data from the file corresponding to the specified collection name and returns a
   * list of items.
   *
   * @param collectionName the name of the collection to parse
   * @return a list of items parsed from the collection
   */
  public List<T> parse(String collectionName) {
    try {
      // get allCollections
      JsonNode node = jsonMapper.readTree(pathToDb.toFile());
      // accessing value of the specified field of an object node,
      // e.g. the JSON array within "items" field of { users:[...], items:[...]}
      JsonNode collection = node.get(collectionName);
      // Send an empty list if there is not the requested collection
      if (collection == null) {
        return (List<T>) new ArrayList<T>();
      }
      // convert the JsonNode to a List of POJOs & return it
      return jsonMapper.readerForListOf(type).readValue(collection);
    } catch (FileNotFoundException e) {
      return (List<T>) new ArrayList<T>(); // send an empty list if there is no db file
    } catch (IOException e) {
      e.printStackTrace();
      return (List<T>) new ArrayList<T>();
    }
  }

  /**
   * Filters the JSON view to include only public fields for a list of items. Serializes the list
   * with public view and then deserializes it back.
   *
   * @param list the list of items to filter
   * @return a filtered list of items with only public fields included
   */
  public <T> List<T> filterPublicJsonViewAsList(List<T> list) {
    try {
      JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, this.type);
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemListAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(list);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJOs)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemListAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Filters the JSON view to include only public fields for a single item.
   *
   * @param item the item to filter
   * @return the filtered item with only public fields included
   */
  public <T> T filterPublicJsonView(T item) {
    try {
      // serialize using JSON Views : public view (all fields not required in the
      // views are not serialized)
      String publicItemAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(item);
      // deserialize using JSON Views : Public View (all fields that are not serialized
      // are set to their default values in the POJO)
      return jsonMapper.readerWithView(Views.Public.class).forType(type)
          .readValue(publicItemAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Serializes a list of items with only public information (specified by Views.Public) and writes
   * it to the file. If the file doesn't exist, creates a new one. Updates the collection specified
   * by collectionName.
   *
   * @param items          the list of items to serialize
   * @param collectionName the name of the collection to update or create
   */
  public void serializePublicInfoOnly(List<T> items, String collectionName) {
    try {
      String currentCollectionAsString = jsonMapper.writerWithView(Views.Public.class)
          .writeValueAsString(items);
      JsonNode updatedCollection = jsonMapper.readTree(currentCollectionAsString);
      // if no DB file, write a new collection to a new db file
      if (!Files.exists(pathToDb)) {
        // Create an object and add a JSON array as POJO, e.g. { items:[...]}
        ObjectNode newCollection = jsonMapper.createObjectNode()
            .putPOJO(collectionName, updatedCollection);
        jsonMapper.writeValue(pathToDb.toFile(),
            newCollection); // write the JSON Object in the DB file
        return;
      }
      // get all collections : can be read as generic JsonNode, if it can be Object or Array;
      JsonNode allCollections = jsonMapper.readTree(
          pathToDb.toFile()); // e.g. { users:[...], items:[...]}
      // remove current collection, e.g. remove the array of items
      if (allCollections.has(collectionName)) {
        ((ObjectNode) allCollections).remove(collectionName); //e.g. it leaves { users:[...]}
      }

      // Prepare a JSON array from the list of POJOs for the collection to be updated
      //ArrayNode updatedCollection = jsonMapper.valueToTree(items);

      // Add the JSON array in allCollections, e.g. : { users:[...], items:[...]}
      ((ObjectNode) allCollections).putPOJO(collectionName, updatedCollection);
      // write to the db file allCollections
      jsonMapper.writeValue(pathToDb.toFile(), allCollections);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}