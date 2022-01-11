package net.bank.transactions.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import net.bank.transactions.common.exception.CodeException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AmountExceptionTest {

  @Test
  void test() {
    AmountException amountException = null;

    assertNull(amountException);

    amountException = new AmountException(CodeException.ERR_0001);

    assertEquals(HttpStatus.CONFLICT, amountException.getHttpStatusCode());
    assertEquals(HttpStatus.CONFLICT.value(), amountException.getCode());

  }
}