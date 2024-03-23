package be.vinci.pae.utils;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Throwable e) {
    super(e);
  }
}
