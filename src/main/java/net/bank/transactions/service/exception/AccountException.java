package net.bank.transactions.service.exception;

import net.bank.transactions.common.exception.CodeException;
import org.springframework.http.HttpStatus;

@SuppressWarnings({"java:S110"})
public class AccountException extends ResponseException {

  private static final HttpStatus status = HttpStatus.CONFLICT;

  public AccountException(CodeException exception, String description) {
    super(status.value(), exception.getDescription(), description);
  }

  @Override
  public HttpStatus getHttpStatusCode() {
    return status;
  }
}
