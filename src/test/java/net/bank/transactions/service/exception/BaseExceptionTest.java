package net.bank.transactions.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import net.bank.transactions.common.exception.CodeException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class BaseExceptionTest {

  @Test
  void test_withoutDescription() {
    BaseException baseException = null;

    assertNull(baseException);

    baseException = new BaseException(HttpStatus.CONFLICT.value(),
        CodeException.ERR_0001.getDescription());

    assertEquals(CodeException.ERR_0001.getDescription(), baseException.getMessage());
    assertNull(baseException.getDescription());
    assertEquals(HttpStatus.CONFLICT.value(), baseException.getCode());

  }

  @Test
  void test_withDescription() {
    BaseException baseException = null;

    assertNull(baseException);

    baseException = new BaseException(HttpStatus.CONFLICT.value(),
        CodeException.ERR_0001.getDescription(), CodeException.ERR_0001.getDescription());

    assertEquals(CodeException.ERR_0001.getDescription(), baseException.getMessage());
    assertEquals(CodeException.ERR_0001.getDescription(), baseException.getDescription());
    assertEquals(HttpStatus.CONFLICT.value(), baseException.getCode());

  }

  @Test
  void test_withCause() {
    BaseException baseException = null;

    assertNull(baseException);

    Throwable exception = new RuntimeException();

    baseException = new BaseException(HttpStatus.CONFLICT.value(),
        CodeException.ERR_0001.getDescription(), CodeException.ERR_0001.getDescription(),
        exception);

    assertEquals(CodeException.ERR_0001.getDescription(), baseException.getMessage());
    assertEquals(CodeException.ERR_0001.getDescription(), baseException.getDescription());
    assertEquals(HttpStatus.CONFLICT.value(), baseException.getCode());
    assertEquals(exception, baseException.getCause());

  }

}