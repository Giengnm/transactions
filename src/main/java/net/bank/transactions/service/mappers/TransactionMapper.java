package net.bank.transactions.service.mappers;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.domain.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface TransactionMapper {

  @Mapping(target = "reference", expression = "java(checkReference(transaction.getReference()))")
  @Mapping(target = "date", expression = "java(checkDate(transaction.getDate()))")
  TransactionEntity toNewEntity(TransactionDTO transaction);

  TransactionDTO toTransactionDTO(TransactionEntity transaction);

  TransactionStatusDTO toTransactionStatusDTO(TransactionEntity transaction);

  default String checkReference(String value) {
    return Optional.ofNullable(value).orElse(UUID.randomUUID().toString().substring(1, 6));
  }

  @Mapping(target = "date", expression = "java(Date.from(transaction.getDate()))")
  default Instant checkDate(Date value) {
    return Optional.ofNullable(value).orElse(new Date()).toInstant();
  }

}
