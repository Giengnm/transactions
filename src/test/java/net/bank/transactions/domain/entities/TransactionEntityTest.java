package net.bank.transactions.domain.entities;

import static net.bank.transactions.api.utilities.TransactionUtilities.ACCOUNT_IBAN;
import static net.bank.transactions.api.utilities.TransactionUtilities.AMOUNT;
import static net.bank.transactions.api.utilities.TransactionUtilities.DESCRIPTION;
import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.FEE;
import static net.bank.transactions.api.utilities.TransactionUtilities.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TransactionEntityTest {

  @Test
  void test() {
    TransactionEntity transaction = null;

    assertNull(transaction);

    transaction = new TransactionEntity();

    assertNotNull(transaction);
    assertNull(transaction.getId(), "The id is not null");

    UUID id = UUID.randomUUID();
    transaction.setId(id);

    assertNotNull(transaction.getId(), "The id is null");
    assertEquals(id, transaction.getId());
    assertNull(transaction.getReference(), "The reference is not null");

    transaction.setReference(FAIL_REFERENCE);

    assertNotNull(transaction.getReference(), "The reference is null");
    assertEquals(FAIL_REFERENCE, transaction.getReference());
    assertNull(transaction.getAccountIban(), "The account-iban is not null");

    transaction.setAccountIban(ACCOUNT_IBAN);

    assertNotNull(transaction.getAccountIban(), "The account-iban is null");
    assertEquals(ACCOUNT_IBAN, transaction.getAccountIban());
    assertNull(transaction.getDate(), "The date is not null");

    Instant date = Instant.now();
    transaction.setDate(date);

    assertNotNull(transaction.getDate().toString(), "The date is null");
    assertEquals(date, transaction.getDate());
    assertNull(transaction.getDescription(), "The description is not null");

    transaction.setDescription(DESCRIPTION);

    assertNotNull(transaction.getDescription(), "The description is null");
    assertEquals(DESCRIPTION, transaction.getDescription());
    assertEquals(ZERO, transaction.getAmount(), "The amount is not null");

    transaction.setAmount(AMOUNT);

    assertEquals(AMOUNT, transaction.getAmount(), "The amount is null");
    assertEquals(ZERO, transaction.getFee(), "The fee is not null");

    transaction.setFee(FEE);

    assertEquals(FEE, transaction.getFee(), "The fee is null");
  }

}