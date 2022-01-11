package net.bank.transactions.service.exception;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends BaseException {

  protected ResponseException(int code, String message) {
    super(code, message);
  }

  protected ResponseException(int code, String message, String description) {
    super(code, message, description);
  }

  public abstract HttpStatus getHttpStatusCode();

}

