package net.bank.transactions.api;

import static net.bank.transactions.common.Constants.API_VERSION;
import static net.bank.transactions.common.Constants.RESPONSE_CODE_CONFLICT;
import static net.bank.transactions.common.Constants.RESPONSE_CODE_FORBIDDEN;
import static net.bank.transactions.common.Constants.RESPONSE_CODE_INTERNAL_SERVER_ERROR;
import static net.bank.transactions.common.Constants.RESPONSE_CODE_OK;
import static net.bank.transactions.common.Constants.RESPONSE_CODE_UNAUTHORIZED;
import static net.bank.transactions.common.Constants.RESPONSE_DESCRIPTION_CONFLICT;
import static net.bank.transactions.common.Constants.RESPONSE_DESCRIPTION_FORBIDDEN;
import static net.bank.transactions.common.Constants.RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR;
import static net.bank.transactions.common.Constants.RESPONSE_DESCRIPTION_OK;
import static net.bank.transactions.common.Constants.RESPONSE_DESCRIPTION_UNAUTHORIZED;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import net.bank.transactions.api.dto.TransactionDTO;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.common.enums.ChannelEnum;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@RequestMapping(API_VERSION + "/transaction")
@Tag(name = "transactions", description = "the transactions API")
public interface TransactionController {

  String TAG = "transaction";

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create transaction",
      description =
          "This endpoint will receive the transaction information and store it into the system."
              + "It is IMPORTANT to note that a transaction that leaves the total account balance"
              + " bellow 0 is not allowed.",
      tags = {TAG})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
          description = RESPONSE_DESCRIPTION_OK,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = TransactionDTO.class)
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
          description = RESPONSE_DESCRIPTION_UNAUTHORIZED,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_UNAUTHORIZED + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_UNAUTHORIZED + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_FORBIDDEN,
          description = RESPONSE_DESCRIPTION_FORBIDDEN,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_FORBIDDEN + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_FORBIDDEN + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT,
          description = RESPONSE_DESCRIPTION_CONFLICT,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_CONFLICT + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_CONFLICT + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR,
          description = RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_INTERNAL_SERVER_ERROR + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      )
  })
  Mono<ResponseEntity<TransactionDTO>> addTransaction(
      @RequestBody(required = true) TransactionDTO transaction);

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Query transactions by IBAN",
      description = "This endpoint searches for transactions and should be able to.",
      tags = {TAG})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
          description = RESPONSE_DESCRIPTION_OK,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = TransactionDTO.class)
              )
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
          description = RESPONSE_DESCRIPTION_UNAUTHORIZED,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_UNAUTHORIZED + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_UNAUTHORIZED + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_FORBIDDEN,
          description = RESPONSE_DESCRIPTION_FORBIDDEN,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_FORBIDDEN + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_FORBIDDEN + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT,
          description = RESPONSE_DESCRIPTION_CONFLICT,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_CONFLICT + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_CONFLICT + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR,
          description = RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_INTERNAL_SERVER_ERROR + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      )
  })
  Mono<ResponseEntity<List<TransactionDTO>>> getTransactions(
      @RequestParam("account-iban") final String accountIban,
      @RequestParam("order") final Sort.Direction order);

  @GetMapping(value = "/status",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Query transactions status by reference",
      description =
          "This endpoint, based on the payload and some business rules, will return the status and "
              + "additional information for a specific transaction.",
      tags = {TAG})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
          description = RESPONSE_DESCRIPTION_OK,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(
                  schema = @Schema(implementation = TransactionDTO.class)
              )
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
          description = RESPONSE_DESCRIPTION_UNAUTHORIZED,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_UNAUTHORIZED + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_UNAUTHORIZED + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_FORBIDDEN,
          description = RESPONSE_DESCRIPTION_FORBIDDEN,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_FORBIDDEN + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_FORBIDDEN + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT,
          description = RESPONSE_DESCRIPTION_CONFLICT,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_CONFLICT + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_CONFLICT + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      ),
      @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER_ERROR,
          description = RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR,
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              examples = @ExampleObject(value = "{\n" +
                  "  \"timestamp\": \"2021-01-13T11:48:01.590+00:00\",\n" +
                  "  \"exception\": \"ExampleException\",\n" +
                  "  \"requestId\": \"5406f7f1-1\",\n" +
                  "  \"status\": \"" + RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR + "\",\n" +
                  "  \"error\": " + RESPONSE_CODE_INTERNAL_SERVER_ERROR + ",\n" +
                  "  \"message\": \"My example exception!!\"\n" +
                  "}")
          )
      )
  })
  Mono<ResponseEntity<TransactionStatusDTO>> getTransactionsStatus(
      @RequestParam("reference") final String reference,
      @RequestParam(value = "channel", required = false) final ChannelEnum channel);

}
