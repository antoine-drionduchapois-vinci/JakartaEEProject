package be.vinci.pae.utils;

/**
 * The FatalErrorException class represents an unrecoverable error that causes the program to
 * terminate. It extends the RuntimeException class and is typically used to wrap other exceptions
 * that indicate critical failures in the application.
 */
public class FatalErrorException extends RuntimeException {

  /**
   * Constructs a new FatalErrorException with the specified cause.
   *
   * @param e The cause of the fatal error, typically another Throwable instance.
   */
  public FatalErrorException(Throwable e) {
    super(e);
  }
}
