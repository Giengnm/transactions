package net.bank.transactions.service.impl;

import static net.bank.transactions.api.utilities.TransactionUtilities.ACCOUNT_IBAN;
import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionToday;
import static org.mockito.Mockito.when;

import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.utilities.TransactionUtilities;
import net.bank.transactions.common.enums.ChannelEnum;
import net.bank.transactions.domain.entities.TransactionEntity;
import net.bank.transactions.domain.repository.TransactionRepository;
import net.bank.transactions.service.exception.AccountException;
import net.bank.transactions.service.exception.AmountException;
import net.bank.transactions.service.mappers.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort.Direction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock
  private TransactionRepository transactionRepository;

  @Spy
  private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  void test_addTransactionException() {
    StepVerifier.create(transactionService.addTransaction(new TransactionDTO()))
        .expectError(AccountException.class)
        .verify();
  }

  @Test
  void test_addTransactionMapperException() {
    TransactionDTO transaction = new TransactionDTO();
    transaction.setAmount(1.12f);
    StepVerifier.create(transactionService.addTransaction(transaction))
        .expectError(AccountException.class)
        .verify();
  }

  @Test
  void test_addTransactionAmountException() {
    TransactionDTO transaction = new TransactionDTO();
    transaction.setAccountIban(ACCOUNT_IBAN);
    StepVerifier.create(transactionService.addTransaction(transaction))
        .expectError(AmountException.class)
        .verify();
  }

  @Test
  void test_addTransactionWithoutException() {
    when(transactionRepository.save(Mockito.any(TransactionEntity.class)))
        .thenReturn(TransactionUtilities.getMonoTransactionEntity());

    StepVerifier.create(transactionService.addTransaction(getTransactionToday(FAIL_REFERENCE)))
        .expectNextMatches(TransactionUtilities::validateTransactionDTO)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsReturnNothing() {
    when(transactionRepository.findByAccountIban(Mockito.anyString(), Mockito.any()))
        .thenReturn(Flux.empty());

    StepVerifier.create(transactionService.getTransactions(ACCOUNT_IBAN, Direction.DESC))
        .expectNextCount(0)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsReturnData() {
    when(transactionRepository.findByAccountIban(Mockito.anyString(), Mockito.any()))
        .thenReturn(TransactionUtilities.getFluxTransactionEntity());

    StepVerifier.create(transactionService.getTransactions(ACCOUNT_IBAN, Direction.DESC))
        .expectNextMatches(TransactionUtilities::validateTransactionDTO)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnNothing_ATMChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.ATM))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOInvalid)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnNothing_ClientChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.CLIENT))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOInvalid)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnNothing_InternalChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(Mono.empty());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.INTERNAL))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOInvalid)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataToday_ATMChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntity());

    StepVerifier.create(transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.ATM))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOPendingATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataToday_ClientChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntity());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.CLIENT))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOPendingATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataToday_InternalChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntity());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.INTERNAL))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOPendingInternal)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataYesterday_ATMChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityYesterday());

    StepVerifier.create(transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.ATM))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOSettledATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataYesterday_ClientChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityYesterday());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.CLIENT))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOSettledATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataYesterday_InternalChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityYesterday());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.INTERNAL))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOSettledInternal)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataTomorrow_ATMChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityTomorrow());

    StepVerifier.create(transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.ATM))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOPendingATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataTomorrow_ClientChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityTomorrow());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.CLIENT))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOFutureATMOrClient)
        .verifyComplete();
  }

  @Test
  void test_getTransactionsStatusQueryReturnDataTomorrow_InternalChanel() {
    when(transactionRepository.findByReference(Mockito.anyString()))
        .thenReturn(TransactionUtilities.getMonoTransactionEntityTomorrow());

    StepVerifier.create(
            transactionService.getTransactionsStatus(FAIL_REFERENCE, ChannelEnum.INTERNAL))
        .expectNextMatches(TransactionUtilities::validateTransactionStatusDTOFutureInternal)
        .verifyComplete();
  }

}