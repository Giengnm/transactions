package net.bank.transactions.api.exception;

import net.bank.transactions.common.exception.CodeException;
import net.bank.transactions.service.exception.ResponseException;
import org.springframework.http.HttpStatus;

@SuppressWarnings({"java:S110"})
public class NotFoundException extends ResponseException {

  private static final HttpStatus status = HttpStatus.NOT_FOUND;

  public NotFoundException(CodeException exception) {
    super(status.value(), exception.getCode(), exception.getDescription());
  }

  @Override
  public HttpStatus getHttpStatusCode() {
    return status;
  }
}