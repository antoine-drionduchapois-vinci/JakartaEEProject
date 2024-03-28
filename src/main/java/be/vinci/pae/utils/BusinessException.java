package be.vinci.pae.utils;

/**
 * The BusinessException class represents an exception that occurs due to business logic errors. It
 * extends the Exception class and provides additional functionality to include error codes.
 */
public class BusinessException extends RuntimeException {

  /**
   * Status code of the error.
   */
  private int code;

  /**
   * Constructs a new BusinessException with the specified error message.
   *
   * @param message The error message describing the exception.
   */
  public BusinessException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessException with the specified error code and message.
   *
   * @param code    The error code associated with the exception.
   * @param message The error message describing the exception.
   */
  public BusinessException(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Retrieves the error code associated with this BusinessException.
   *
   * @return The error code.
   */
  public int getCode() {
    return code;
  }
}
