package be.vinci.pae.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ConfigTest {
  static {
    Config.load("dev.properties");
  }



  @Test
  void testGetProperty() {
    // Vérifier la récupération d'une propriété existante
    assertEquals("http://localhost:8080/", Config.getProperty("BaseUri"));

    // Vérifier la récupération d'une propriété inexistante
    assertNull(Config.getProperty("nonExistentKey"));
  }


  @Test
  void testGetBoolProperty() {

    assertEquals(false, Config.getBoolProperty("debugMode"));


  }
}
