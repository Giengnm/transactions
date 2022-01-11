package net.bank.transactions.service.exception;

public class BaseException extends RuntimeException {

  private final int code;
  private final String description;

  public BaseException(final int code, final String message) {
    super(message);
    this.code = code;
    this.description = null;
  }

  public BaseException(final int code, final String message, final String description) {
    super(message);
    this.code = code;
    this.description = description;
  }


  public BaseException(final int code, final String message, final String description,
      final Throwable cause) {
    super(message, cause);
    this.code = code;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

}
