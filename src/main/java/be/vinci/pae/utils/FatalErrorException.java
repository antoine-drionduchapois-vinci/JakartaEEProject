package be.vinci.pae.utils;

public class FatalErrorException extends RuntimeException{
  public FatalErrorException(Throwable e) {
    super(e);
  }
}
