package net.bank.transactions.common.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChannelEnumTest {

  private static final String CLIENT = "CLIENT";
  private static final String INTERNAL = "INTERNAL";
  private static final String ATM = "ATM";
  private static final ChannelEnum CLIENT_ENUM = ChannelEnum.CLIENT;
  private static final ChannelEnum INTERNAL_ENUM = ChannelEnum.INTERNAL;
  private static final ChannelEnum ATM_ENUM = ChannelEnum.ATM;

  @Test
  void isRestFee() {
    assertTrue(CLIENT_ENUM.isRestFee());
    assertTrue(ATM_ENUM.isRestFee());
    assertFalse(INTERNAL_ENUM.isRestFee());
  }

  @Test
  void checkEnum() {
    assertEquals(CLIENT_ENUM, ChannelEnum.valueOf(CLIENT));
    assertNotEquals(CLIENT_ENUM, ChannelEnum.ATM);
    assertEquals(ATM_ENUM, ChannelEnum.valueOf(ATM));
    assertNotEquals(CLIENT_ENUM, ChannelEnum.INTERNAL);
    assertEquals(INTERNAL_ENUM, ChannelEnum.valueOf(INTERNAL));

    assertEquals(CLIENT, CLIENT_ENUM.getChannel());
    assertNotEquals(CLIENT, ChannelEnum.ATM.getChannel());
    assertEquals(ATM, ATM_ENUM.getChannel());
    assertNotEquals(INTERNAL, ChannelEnum.CLIENT.getChannel());
    assertEquals(INTERNAL, INTERNAL_ENUM.getChannel());
  }
}