package net.bank.transactions.api.impl;

import static net.bank.transactions.api.utilities.TransactionUtilities.ACCOUNT_IBAN;
import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.getMonoTransactionDTO;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionBeforeToday;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionDTO;
import static net.bank.transactions.common.exception.CodeException.ERR_0003;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.api.exception.NotFoundException;
import net.bank.transactions.api.utilities.TransactionUtilities;
import net.bank.transactions.common.enums.ChannelEnum;
import net.bank.transactions.service.TransactionService;
import net.bank.transactions.service.exception.AmountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TransactionControllerImplTest {

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionControllerImpl transactionController;

  @Test
  void test_addTransactionReturnError() {
    when(transactionService.addTransaction(Mockito.any()))
        .thenReturn(Mono.error(new AmountException(ERR_0003)));

    StepVerifier
        .create(transactionController.addTransaction(getTransactionBeforeToday(FAIL_REFERENCE)))
        .expectError()
        .verify();
  }

  @Test
  void test_addTransactionReturnData() {
    when(transactionService.addTransaction(Mockito.any()))
        .thenReturn(getMonoTransactionDTO(FAIL_REFERENCE));

    StepVerifier
        .create(transactionController.addTransaction(getTransactionBeforeToday(FAIL_REFERENCE)))
        .expectNextMatches(this::validateResponseAddTransaction)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsReturnError() {
    when(transactionService.getTransactions(Mockito.anyString(), Mockito.any()))
        .thenReturn(Flux.empty());

    StepVerifier
        .create(transactionController.getTransactions(ACCOUNT_IBAN, Direction.DESC))
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void test_getTransactionsReturnData() {
    when(transactionService.getTransactions(Mockito.anyString(), Mockito.any()))
        .thenReturn(Flux.just(getTransactionDTO(FAIL_REFERENCE)));

    StepVerifier
        .create(transactionController.getTransactions(ACCOUNT_IBAN, Direction.DESC))
        .expectNextMatches(this::validateResponseGetTransactions)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusReturnError() {
    when(transactionService.getTransactionsStatus(Mockito.anyString(), Mockito.any()))
        .thenReturn(Mono.empty());

    StepVerifier
        .create(transactionController.getTransactionsStatus(ACCOUNT_IBAN, ChannelEnum.ATM))
        .expectError(NotFoundException.class)
        .verify();
  }

  @Test
  void test_getTransactionsStatusReturnData() {
    when(transactionService.getTransactionsStatus(Mockito.anyString(), Mockito.any()))
        .thenReturn(TransactionUtilities.getMonoTransactionStatusDTO());

    StepVerifier
        .create(transactionController.getTransactionsStatus(ACCOUNT_IBAN, ChannelEnum.ATM))
        .expectNextMatches(this::validateResponseGetTransactionsStatus)
        .verifyComplete();
  }

  private boolean validateResponseGetTransactionsStatus(
      ResponseEntity<TransactionStatusDTO> transactionStatusDTOResponseEntity) {
    TransactionUtilities.validateTransactionStatusDTOSettledInternal(
        Objects.requireNonNull(transactionStatusDTOResponseEntity.getBody()));
    return true;
  }

  private boolean validateResponseAddTransaction(
      ResponseEntity<TransactionDTO> transactionDTOResponseEntity) {
    TransactionUtilities.validateTransactionDTO(
        Objects.requireNonNull(transactionDTOResponseEntity.getBody()));
    return true;
  }


  private boolean validateResponseGetTransactions(
      ResponseEntity<List<TransactionDTO>> listResponseEntity) {
    Objects.requireNonNull(listResponseEntity.getBody())
        .stream()
        .map(TransactionUtilities::validateTransactionDTO);
    return true;
  }
}