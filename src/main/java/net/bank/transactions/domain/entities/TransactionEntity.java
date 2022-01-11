package net.bank.transactions.domain.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transactions")
@Getter
@Setter
public class TransactionEntity implements Serializable {

  @Id
  @Column("id")
  private UUID id;

  @Column("reference")
  private String reference;

  @Column("account_iban")
  @NotEmpty
  private String accountIban;

  @Column("date")
  private Instant date;

  @Column("amount")
  @NotEmpty
  private float amount;

  @Column("fee")
  private float fee;

  @Column("description")
  private String description;

}
