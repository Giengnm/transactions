package net.bank.transactions.service.exception;

import net.bank.transactions.common.exception.CodeException;
import org.springframework.http.HttpStatus;

@SuppressWarnings({"java:S110"})
public class AmountException extends ResponseException {

  private static final HttpStatus status = HttpStatus.CONFLICT;

  public AmountException(CodeException exception) {
    super(status.value(), exception.getCode());
  }

  @Override
  public HttpStatus getHttpStatusCode() {
    return status;
  }
}
