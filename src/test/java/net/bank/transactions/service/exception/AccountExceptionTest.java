package net.bank.transactions.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import net.bank.transactions.common.exception.CodeException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AccountExceptionTest {

  public static final String EXCEPTION = "Exception";

  @Test
  void test() {
    AccountException accountException = null;

    assertNull(accountException);

    accountException = new AccountException(CodeException.ERR_0001, EXCEPTION);

    assertEquals(HttpStatus.CONFLICT, accountException.getHttpStatusCode());
    assertEquals(EXCEPTION, accountException.getDescription());
    assertEquals(HttpStatus.CONFLICT.value(), accountException.getCode());

  }
}