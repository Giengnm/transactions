package net.bank.transactions.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CodeExceptionTest {

  private static final String CODE_0001 = "ERR-0001";
  private static final String CODE_0002 = "ERR-0002";
  private static final String CODE_0003 = "ERR-0003";
  private static final CodeException ERR_0001 = CodeException.ERR_0001;
  private static final CodeException ERR_0002 = CodeException.ERR_0002;
  private static final CodeException ERR_0003 = CodeException.ERR_0003;

  @Test
  void checkEnum() {
    assertEquals(CODE_0001, ERR_0001.getCode());
    assertEquals(CODE_0002, ERR_0002.getCode());
    assertEquals(CODE_0003, ERR_0003.getCode());

    assertEquals(CodeException.ERR_0001.getDescription(), ERR_0001.getDescription());
    assertEquals(CodeException.ERR_0002.getDescription(), ERR_0002.getDescription());
    assertEquals(CodeException.ERR_0003.getDescription(), ERR_0003.getDescription());
  }

}