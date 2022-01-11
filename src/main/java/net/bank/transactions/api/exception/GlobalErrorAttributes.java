package net.bank.transactions.api.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import net.bank.transactions.service.exception.ResponseException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";

  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request,
      ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = new LinkedHashMap<>();

    Throwable error = this.getError(request);

    errorAttributes.put("timestamp", new Date());
    errorAttributes.put("exception", error.getClass().getSimpleName());
    errorAttributes.put("requestId", request.exchange().getRequest().getId());

    if (error instanceof ResponseStatusException) {
      return responseStatusException(errorAttributes, error);
    } else if (error instanceof ResponseException) {
      return responseException(errorAttributes, error);
    } else {
      return defaultException(errorAttributes, error);
    }
  }

  private Map<String, Object> responseException(
      Map<String, Object> errorAttributes,
      Throwable error) {
    ResponseException responseException = (ResponseException) error;
    errorAttributes.put(STATUS, responseException.getHttpStatusCode());
    errorAttributes.put(ERROR, responseException.getMessage());
    errorAttributes.put(MESSAGE, responseException.getDescription());
    return errorAttributes;
  }

  private Map<String, Object> responseStatusException(Map<String, Object> errorAttributes,
      Throwable error) {
    HttpStatus errorStatus = ((ResponseStatusException) error).getStatus();
    errorAttributes.put(STATUS, errorStatus);
    errorAttributes.put(ERROR, errorStatus.getReasonPhrase());
    errorAttributes.put(MESSAGE, error.getMessage());
    return errorAttributes;
  }

  private Map<String, Object> defaultException(Map<String, Object> errorAttributes,
      Throwable error) {
    HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    errorAttributes.put(STATUS, errorStatus);
    errorAttributes.put(ERROR, errorStatus.getReasonPhrase());
    errorAttributes.put(MESSAGE, error.getMessage());
    return errorAttributes;
  }
}
