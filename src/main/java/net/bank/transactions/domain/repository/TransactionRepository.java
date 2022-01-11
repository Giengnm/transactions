package net.bank.transactions.domain.repository;

import net.bank.transactions.domain.entities.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends R2dbcRepository<TransactionEntity, String> {

  Flux<TransactionEntity> findByAccountIban(final String accountIban, Sort sort);

  Mono<TransactionEntity> findByReference(final String reference);
}
