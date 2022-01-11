package net.bank.transactions.api.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.common.enums.StatusEnum;
import net.bank.transactions.domain.entities.TransactionEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TransactionUtilities {

  public static final String FAIL_REFERENCE = "XXXXXX";
  public static final float ZERO = 0f;
  public static final int AMOUNT_TO_SUBTRACT = 1;
  public static final String ACCOUNT_IBAN = "ES9820385778983000760236";
  public static final float AMOUNT = 193.38f;
  public static final float FEE = 3.18f;
  public static final String DESCRIPTION = "Description";
  public static final StatusEnum STATUS_INVALID_ENUM = StatusEnum.INVALID;
  public static final Instant INSTANT_TRANSACTION = Instant.now();
  private static final StatusEnum STATUS_PENDING_ENUM = StatusEnum.PENDING;
  private static final StatusEnum STATUS_SETTLED_ENUM = StatusEnum.SETTLED;
  private static final StatusEnum STATUS_FUTURE_ENUM = StatusEnum.FUTURE;

  public static HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  public static TransactionDTO getTransactionBeforeToday(final String reference) {
    TransactionDTO transaction = getTransactionDTO(reference);
    transaction.setDate(Date.from(INSTANT_TRANSACTION.minus(AMOUNT_TO_SUBTRACT, ChronoUnit.DAYS)));
    return transaction;
  }

  public static TransactionDTO getTransactionToday(final String reference) {
    TransactionDTO transaction = getTransactionDTO(reference);
    transaction.setDate(Date.from(INSTANT_TRANSACTION));
    return transaction;
  }

  public static TransactionDTO getTransactionAfterToday(final String reference) {
    TransactionDTO transaction = getTransactionDTO(reference);
    transaction.setDate(Date.from(INSTANT_TRANSACTION.plus(AMOUNT_TO_SUBTRACT, ChronoUnit.DAYS)));
    return transaction;
  }

  public static TransactionDTO getTransactionDTO(final String reference) {
    TransactionDTO transaction = new TransactionDTO();
    transaction.setReference(reference);
    transaction.setAccountIban(ACCOUNT_IBAN);
    transaction.setAmount(AMOUNT);
    transaction.setFee(FEE);
    transaction.setDate(Date.from(INSTANT_TRANSACTION));
    transaction.setDescription(DESCRIPTION);
    return transaction;
  }

  public static Mono<TransactionDTO> getMonoTransactionDTO(final String reference) {
    return Mono.just(getTransactionDTO(reference));
  }

  public static boolean validateTransactionDTO(TransactionDTO transactionDTO) {
    assertEquals(FAIL_REFERENCE, transactionDTO.getReference());
    assertEquals(ACCOUNT_IBAN, transactionDTO.getAccountIban());
    assertEquals(DESCRIPTION, transactionDTO.getDescription());
    assertEquals(Date.from(INSTANT_TRANSACTION), transactionDTO.getDate());
    assertEquals(AMOUNT, transactionDTO.getAmount(), ZERO);
    assertEquals(FEE, transactionDTO.getFee(), ZERO);
    return true;
  }

  public static TransactionEntity getTransactionEntity() {
    TransactionEntity transaction = new TransactionEntity();
    transaction.setId(UUID.randomUUID());
    transaction.setReference(FAIL_REFERENCE);
    transaction.setAccountIban(ACCOUNT_IBAN);
    transaction.setDate(INSTANT_TRANSACTION);
    transaction.setAmount(AMOUNT);
    transaction.setFee(FEE);
    transaction.setDescription(DESCRIPTION);
    return transaction;
  }

  public static Mono<TransactionEntity> getMonoTransactionEntity() {
    return Mono.just(getTransactionEntity());
  }

  public static Flux<TransactionEntity> getFluxTransactionEntity() {
    return Flux.just(getTransactionEntity());
  }

  public static boolean validateTransactionStatusDTOInvalid(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_INVALID_ENUM, transactionStatusDTO.getStatus());
    assertEquals(ZERO, transactionStatusDTO.getAmount());
    assertEquals(ZERO, transactionStatusDTO.getFee());
    return true;
  }

  public static Mono<TransactionEntity> getMonoTransactionEntityYesterday() {
    TransactionEntity transactionEntity = getTransactionEntity();
    transactionEntity.setDate(INSTANT_TRANSACTION.minus(AMOUNT_TO_SUBTRACT, ChronoUnit.DAYS));
    return Mono.just(transactionEntity);
  }

  public static Mono<TransactionEntity> getMonoTransactionEntityTomorrow() {
    TransactionEntity transactionEntity = getTransactionEntity();
    transactionEntity.setDate(INSTANT_TRANSACTION.plus(AMOUNT_TO_SUBTRACT, ChronoUnit.DAYS));
    return Mono.just(transactionEntity);
  }

  public static boolean validateTransactionStatusDTOPendingATMOrClient(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_PENDING_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT - FEE, transactionStatusDTO.getAmount());
    assertEquals(ZERO, transactionStatusDTO.getFee());
    return true;
  }

  public static boolean validateTransactionStatusDTOPendingInternal(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_PENDING_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT, transactionStatusDTO.getAmount());
    assertEquals(FEE, transactionStatusDTO.getFee());
    return true;
  }

  public static boolean validateTransactionStatusDTOSettledATMOrClient(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_SETTLED_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT - FEE, transactionStatusDTO.getAmount());
    assertEquals(ZERO, transactionStatusDTO.getFee());
    return true;
  }

  public static boolean validateTransactionStatusDTOSettledInternal(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_SETTLED_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT, transactionStatusDTO.getAmount());
    assertEquals(FEE, transactionStatusDTO.getFee());
    return true;
  }

  public static boolean validateTransactionStatusDTOFutureATMOrClient(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_FUTURE_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT - FEE, transactionStatusDTO.getAmount());
    assertEquals(ZERO, transactionStatusDTO.getFee());
    return true;
  }

  public static boolean validateTransactionStatusDTOFutureInternal(
      TransactionStatusDTO transactionStatusDTO) {
    assertEquals(FAIL_REFERENCE, transactionStatusDTO.getReference());
    assertEquals(STATUS_FUTURE_ENUM, transactionStatusDTO.getStatus());
    assertEquals(AMOUNT, transactionStatusDTO.getAmount());
    assertEquals(FEE, transactionStatusDTO.getFee());
    return true;
  }

  public static Mono<TransactionStatusDTO> getMonoTransactionStatusDTO() {
    return Mono.just(getTransactionStatusDTO());
  }

  private static TransactionStatusDTO getTransactionStatusDTO() {
    return TransactionStatusDTO
        .builder()
        .reference(FAIL_REFERENCE)
        .status(STATUS_SETTLED_ENUM)
        .amount(AMOUNT)
        .fee(FEE)
        .build();
  }
}
