package net.bank.transactions.cucumber;

import static net.bank.transactions.api.utilities.TransactionUtilities.FAIL_REFERENCE;
import static net.bank.transactions.api.utilities.TransactionUtilities.ZERO;
import static net.bank.transactions.api.utilities.TransactionUtilities.getHeaders;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionAfterToday;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionBeforeToday;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionToday;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.bank.transactions.api.dto.TransactionStatusDTO;
import net.bank.transactions.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class TransactionsStep {

  private String reference = "";

  @Autowired
  protected TestRestTemplate template;

  private ResponseEntity response;
  protected ObjectMapper objectMapper = new ObjectMapper();
  private TransactionStatusDTO transactionStatus = null;

  @Given("A transaction that is not stored in our system")
  public void aTransactionThatIsNotStoredInOurSystem() {
    // The system begins empty
  }

  @When("I check the status from any channel")
  public void iCheckTheStatusFromAnyChannel() {
    String path = "/api/v1/transaction/status?reference=" + FAIL_REFERENCE;
    response = template.getForEntity(path, TransactionStatusDTO.class);
  }

  @Then("The system returns the status {string}")
  public void theSystemReturnsTheStatusINVALID(String status) {
    transactionStatus = (TransactionStatusDTO) response.getBody();
    assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    assert transactionStatus != null;
    assertEquals(StatusEnum.valueOf(status), transactionStatus.getStatus());
  }

  @Given("A transaction that is stored in our system and the transaction date is before today")
  public void aTransactionThatIsStoredInOurSystemAndTransactionDateIsBeforeToday() {
    reference = UUID.randomUUID().toString().substring(1, 6);
    HttpEntity requestEntity = new HttpEntity(getTransactionBeforeToday(reference), getHeaders());

    String path = "/api/v1/transaction";
    response = template.postForEntity(path, requestEntity, Object.class);
  }

  @Given("A transaction that is stored in our system and the transaction date is equals to today")
  public void aTransactionThatIsStoredInOurSystemAndTheTransactionDateIsEqualsToToday() {
    reference = UUID.randomUUID().toString().substring(1, 6);
    HttpEntity requestEntity = new HttpEntity(getTransactionToday(reference), getHeaders());

    String path = "/api/v1/transaction";
    response = template.postForEntity(path, requestEntity, Object.class);
  }

  @Given("A transaction that is stored in our system and the transaction date is greater than today")
  public void aTransactionThatIsStoredInOurSystemAndTheTransactionDateIsGreaterThanToday() {
    reference = UUID.randomUUID().toString().substring(1, 6);
    HttpEntity requestEntity = new HttpEntity(getTransactionAfterToday(reference), getHeaders());

    String path = "/api/v1/transaction";
    response = template.postForEntity(path, requestEntity, Object.class);
  }

  @When("I check the status of reference from {string} channel")
  public void iCheckTheStatusFromAChannel(String channel) {
    String path =
        "/api/v1/transaction/status?reference=" + reference + "&channel=" + channel;
    response = template.getForEntity(path, TransactionStatusDTO.class);
  }

  @And("the amount subtracting the fee")
  public void theAmountSubtractingTheFee() {
    assertEquals(getTransactionBeforeToday(reference).getAmount(), transactionStatus.getAmount(),
        getTransactionBeforeToday(reference).getFee());
    assertEquals(ZERO, transactionStatus.getFee(), ZERO);
  }

  @And("the amount")
  public void theAmount() {
    assertEquals(getTransactionBeforeToday(reference).getAmount(), transactionStatus.getAmount(),
        ZERO);
  }

  @And("the fee")
  public void theFee() {
    assertEquals(getTransactionBeforeToday(reference).getFee(), transactionStatus.getFee(),
        ZERO);
  }

}
