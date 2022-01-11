package net.bank.transactions.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bank.transactions.common.enums.StatusEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Transaction status", description = "The transaction status information")
public class TransactionStatusDTO implements Serializable {

  @Schema(description = "The transaction reference number.",
      example = "12345A",
      type = "string")
  @JsonProperty("reference")
  private String reference;

  @Schema(description = "The status of the transaction. It can be any of these values: PENDING, SETTLED, FUTURE, INVALID.",
      example = "SETTLED",
      type = "string")
  @JsonProperty("status")
  private StatusEnum status;

  @Schema(description = "The amount of the transaction.",
      example = "193.38",
      type = "number")
  @JsonProperty("amount")
  @NotEmpty
  private float amount;

  @Schema(description = ": The fee applied to the transaction.",
      example = "3.18",
      type = "number")
  @JsonProperty("fee")
  private float fee;
}
