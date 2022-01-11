package net.bank.transactions.api.impl;

import static net.bank.transactions.common.exception.CodeException.ERR_0001;
import static net.bank.transactions.common.exception.CodeException.ERR_0002;

import java.util.List;
import net.bank.transactions.api.TransactionController;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.api.exception.NotFoundException;
import net.bank.transactions.common.enums.ChannelEnum;
import net.bank.transactions.service.TransactionService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Controller
public class TransactionControllerImpl implements TransactionController {

  private TransactionService transactionService;

  public TransactionControllerImpl(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Override
  public Mono<ResponseEntity<TransactionDTO>> addTransaction(
      @RequestBody final TransactionDTO transaction) {
    return transactionService.addTransaction(transaction)
        .map(ResponseEntity::ok);
  }

  @Override
  public Mono<ResponseEntity<List<TransactionDTO>>> getTransactions(final String accountIban,
      final Sort.Direction order) {
    return transactionService.getTransactions(accountIban, order)
        .switchIfEmpty(Mono.error(new NotFoundException(ERR_0001)))
        .collectList()
        .map(ResponseEntity::ok);
  }

  /**
   * Al ser pocas Apis, las genero en el mismo controller
   */
  @Override
  public Mono<ResponseEntity<TransactionStatusDTO>> getTransactionsStatus(final String reference,
      final ChannelEnum channel) {
    return transactionService.getTransactionsStatus(reference, channel)
        .switchIfEmpty(Mono.error(new NotFoundException(ERR_0002)))
        .map(ResponseEntity::ok);
  }
}
