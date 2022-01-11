package net.bank.transactions.service;

import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.common.enums.ChannelEnum;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

  Mono<TransactionDTO> addTransaction(final TransactionDTO transaction);

  Flux<TransactionDTO> getTransactions(final String iban, final Sort.Direction sort);

  Mono<TransactionStatusDTO> getTransactionsStatus(String reference, ChannelEnum channel);
}
