package net.bank.transactions.common.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class StatusEnumTest {

  private static final String PENDING = "PENDING";
  private static final String SETTLED = "SETTLED";
  private static final String FUTURE = "FUTURE";
  private static final String INVALID = "INVALID";
  private static final StatusEnum PENDING_ENUM = StatusEnum.PENDING;
  private static final StatusEnum SETTLED_ENUM = StatusEnum.SETTLED;
  private static final StatusEnum FUTURE_ENUM = StatusEnum.FUTURE;
  private static final StatusEnum INVALID_ENUM = StatusEnum.INVALID;

  @Test
  void checkEnum() {
    assertEquals(PENDING_ENUM, StatusEnum.valueOf(PENDING));
    assertNotEquals(PENDING_ENUM, StatusEnum.SETTLED);
    assertEquals(SETTLED_ENUM, StatusEnum.valueOf(SETTLED));
    assertNotEquals(SETTLED_ENUM, StatusEnum.FUTURE);
    assertEquals(FUTURE_ENUM, StatusEnum.valueOf(FUTURE));
    assertNotEquals(FUTURE_ENUM, StatusEnum.INVALID);
    assertEquals(INVALID_ENUM, StatusEnum.valueOf(INVALID));

    assertEquals(PENDING, PENDING_ENUM.getStatus());
    assertNotEquals(SETTLED, PENDING_ENUM.getStatus());
    assertEquals(SETTLED, SETTLED_ENUM.getStatus());
    assertNotEquals(FUTURE, SETTLED_ENUM.getStatus());
    assertEquals(FUTURE, FUTURE_ENUM.getStatus());
    assertNotEquals(INVALID, FUTURE_ENUM.getStatus());
    assertEquals(INVALID, INVALID_ENUM.getStatus());
  }

}