package net.bank.transactions.api.dto;

import static net.bank.transactions.api.utilities.TransactionUtilities.ACCOUNT_IBAN;
import static net.bank.transactions.api.utilities.TransactionUtilities.AMOUNT;
import static net.bank.transactions.api.utilities.TransactionUtilities.DESCRIPTION;
import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.FEE;
import static net.bank.transactions.api.utilities.TransactionUtilities.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import org.junit.jupiter.api.Test;

class TransactionDTOTest {

  @Test
  void test() {

    TransactionDTO transaction = null;

    assertNull(transaction);

    transaction = new TransactionDTO();

    assertNotNull(transaction);
    assertNull(transaction.getReference(), "The reference is not null");

    transaction.setReference(FAIL_REFERENCE);

    assertNotNull(transaction.getReference(), "The reference is null");
    assertNull(transaction.getAccountIban(), "The account-iban is not null");

    transaction.setAccountIban(ACCOUNT_IBAN);

    assertNotNull(transaction.getAccountIban(), "The account-iban is null");
    assertNull(transaction.getDate(), "The date is not null");

    transaction.setDate(new Date());

    assertNotNull(transaction.getDate().toString(), "The date is null");
    assertNull(transaction.getDescription(), "The description is not null");

    transaction.setDescription(DESCRIPTION);

    assertNotNull(transaction.getDescription(), "The description is null");
    assertEquals(ZERO, transaction.getAmount(), "The amount is not null");

    transaction.setAmount(AMOUNT);

    assertEquals(AMOUNT, transaction.getAmount(), "The amount is null");
    assertEquals(ZERO, transaction.getFee(), "The fee is not null");

    transaction.setFee(FEE);

    assertEquals(FEE, transaction.getFee(), "The fee is null");
  }

}