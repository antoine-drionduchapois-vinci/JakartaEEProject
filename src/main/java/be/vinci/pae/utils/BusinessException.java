package be.vinci.pae.utils;

public class BusinessException extends Exception {
  private int code;
  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(int code, String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
