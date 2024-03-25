package be.vinci.pae.utils;

/**
 * The NotFoundException class represents an exception that indicates a resource was not found. It
 * extends the RuntimeException class and is typically used to signal that a requested resource does
 * not exist.
 */
public class NotFoundException extends RuntimeException {

  /**
   * Constructs a new NotFoundException with no detail message.
   */
  public NotFoundException() {
    super();
  }
}
