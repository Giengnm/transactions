package net.bank.transactions.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Transaction", description = "The transaction information")
public class TransactionDTO implements Serializable {

  @Schema(description = "The transaction unique reference number in our system. If not present, the system will generate one.",
      example = "12345A",
      type = "string")
  @JsonProperty("reference")
  private String reference;

  @Schema(description = "The IBAN number of the account where the transaction has happened.",
      example = "ES9820385778983000760236",
      required = true,
      maxLength = 24,
      type = "string",
      name = "account_iban")
  @JsonProperty(value = "account_iban", required = true)
  private String accountIban;

  @Schema(description = "Date when the transaction took place.",
      example = "2019-07-16T16:55:42.000Z",
      type = "string")
  @JsonProperty(value = "date")
  private Date date;

  @Schema(description = "If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account)",
      example = "193.38",
      required = true,
      type = "number")
  @JsonProperty(value = "amount", required = true)
  private float amount;

  @Schema(description = " Fee that will be deducted from the amount, regardless on the amount being positive or negative.",
      example = "3.18",
      type = "number")
  @JsonProperty("fee")
  private float fee;

  @Schema(description = "The description of the transaction.",
      example = "Restaurant payment",
      type = "string")
  @JsonProperty("description")
  private String description;

}
