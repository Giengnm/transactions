package net.bank.transactions.service.impl;

import static net.bank.transactions.common.Constants.COLUMN_ORDER_TRANSACTIONS;
import static net.bank.transactions.common.exception.CodeException.ERR_0003;
import static net.bank.transactions.common.exception.CodeException.ERR_0004;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.common.enums.ChannelEnum;
import net.bank.transactions.common.enums.StatusEnum;
import net.bank.transactions.domain.entities.TransactionEntity;
import net.bank.transactions.domain.repository.TransactionRepository;
import net.bank.transactions.service.TransactionService;
import net.bank.transactions.service.exception.AccountException;
import net.bank.transactions.service.exception.AmountException;
import net.bank.transactions.service.mappers.TransactionMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

  private static final float ZERO = 0;
  private TransactionRepository transactionRepository;
  private TransactionMapper transactionMapper;

  public TransactionServiceImpl(final TransactionRepository transactionRepository,
      TransactionMapper transactionMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public Mono<TransactionDTO> addTransaction(final TransactionDTO transaction) {
    return Mono.just(transaction)
        .map(this::validateTransaction)
        .map(transactionMapper::toNewEntity)
        .flatMap(transactionRepository::save)
        .map(transactionMapper::toTransactionDTO);
  }

  private TransactionDTO validateTransaction(TransactionDTO transaction) {

    if (ObjectUtils.isEmpty(transaction.getAccountIban())) {
      throw new AccountException(ERR_0004, "Error Account IBAN is required");
    }

    if (Optional.of(transaction.getAmount()).orElse(ZERO).equals(ZERO)) {
      throw new AmountException(ERR_0003);
    }

    return transaction;
  }

  @Override
  public Flux<TransactionDTO> getTransactions(final String iban, final Sort.Direction sort) {
    return transactionRepository.findByAccountIban(iban,
            Sort.by(sort, COLUMN_ORDER_TRANSACTIONS))
        .map(transactionMapper::toTransactionDTO);
  }

  @Override
  public Mono<TransactionStatusDTO> getTransactionsStatus(String reference, ChannelEnum channel) {
    return transactionRepository.findByReference(reference)
        .map(transaction -> this.funcionalStatus(transaction, channel))
        .switchIfEmpty(Mono.just(TransactionStatusDTO.builder()
            .reference(reference)
            .status(StatusEnum.INVALID)
            .build()));
  }

  private TransactionStatusDTO funcionalStatus(TransactionEntity transaction, ChannelEnum channel) {
    TransactionStatusDTO transactionStatus = transactionMapper.toTransactionStatusDTO(transaction);

    if (isTransactionBeforeToday(transaction.getDate())) {
      transactionStatus.setStatus(StatusEnum.SETTLED);
    } else if (isTransactionToday(transaction.getDate())) {
      transactionStatus.setStatus(StatusEnum.PENDING);
    } else if (channel.equals(ChannelEnum.ATM)) {
      transactionStatus.setStatus(StatusEnum.PENDING);
    } else {
      transactionStatus.setStatus(StatusEnum.FUTURE);
    }

    if (channel.isRestFee()) {
      transactionStatus.setAmount(transactionStatus.getAmount() - transactionStatus.getFee());
      transactionStatus.setFee(0f);
    }

    return transactionStatus;
  }

  private boolean isTransactionBeforeToday(Instant date) {
    return date.isBefore(Instant.now().truncatedTo(ChronoUnit.DAYS));
  }

  private boolean isTransactionToday(Instant date) {
    return date.truncatedTo(ChronoUnit.DAYS).equals(Instant.now().truncatedTo(ChronoUnit.DAYS));
  }
}
