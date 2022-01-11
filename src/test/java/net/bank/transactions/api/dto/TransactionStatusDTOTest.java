package net.bank.transactions.api.dto;

import static net.bank.transactions.api.utilities.TransactionUtilities.AMOUNT;
import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.FEE;
import static net.bank.transactions.api.utilities.TransactionUtilities.STATUS_INVALID_ENUM;
import static net.bank.transactions.api.utilities.TransactionUtilities.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class TransactionStatusDTOTest {


  @Test
  void test_allConstructor() {
    TransactionStatusDTO status = null;

    assertNull(status);

    status = new TransactionStatusDTO(FAIL_REFERENCE, STATUS_INVALID_ENUM, AMOUNT, FEE);

    assertNotNull(status);

    assertEquals(FAIL_REFERENCE, status.getReference());
    assertEquals(STATUS_INVALID_ENUM, status.getStatus());
    assertEquals(AMOUNT, status.getAmount(), ZERO);
    assertEquals(FEE, status.getFee(), ZERO);
  }

  @Test
  void test_builder() {
    TransactionStatusDTO status = null;

    assertNull(status);

    status = TransactionStatusDTO.builder()
        .reference(FAIL_REFERENCE)
        .status(STATUS_INVALID_ENUM)
        .amount(AMOUNT)
        .fee(FEE)
        .build();

    assertNotNull(status);

    assertEquals(FAIL_REFERENCE, status.getReference());
    assertEquals(STATUS_INVALID_ENUM, status.getStatus());
    assertEquals(AMOUNT, status.getAmount(), ZERO);
    assertEquals(FEE, status.getFee(), ZERO);
  }

  @Test
  void test_NoArgConstructor() {
    TransactionStatusDTO status = null;

    assertNull(status);

    status = new TransactionStatusDTO();

    assertNotNull(status);
    assertNull(status.getReference());
    assertNull(status.getStatus());
    assertEquals(ZERO, status.getAmount());
    assertEquals(ZERO, status.getFee());

    status.setReference(FAIL_REFERENCE);
    status.setStatus(STATUS_INVALID_ENUM);
    status.setAmount(AMOUNT);
    status.setFee(FEE);

    assertEquals(FAIL_REFERENCE, status.getReference());
    assertEquals(STATUS_INVALID_ENUM, status.getStatus());
    assertEquals(AMOUNT, status.getAmount());
    assertEquals(FEE, status.getFee());
  }
}