package net.bank.transactions.cucumber;

import static net.bank.transactions.api.utilities.TransactionUtilities.getHeaders;
import static net.bank.transactions.api.utilities.TransactionUtilities.getTransactionToday;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ValidateStep {

  private ResponseEntity<Object> response;
  private String responseRoot;

  @Autowired
  protected TestRestTemplate template;

  @When("the client calls Root URI")
  public void theClientCallsRootURI() {
    responseRoot = template.getRootUri();
  }

  @Then("the client receives response root")
  public void theClientReceivesResponseRoot() {
    assertNotNull(responseRoot);
  }

  @When("the client calls GET {string}")
  public void theClientCallsActuatorHealth(String uri) {
    response = template.getForEntity(uri, Object.class);
  }

  @Then("the client receives response status code of {int}")
  public void theClientReceivesResponseStatusCodeOf(int statusCode) {
    HttpStatus currentStatusCode = response.getStatusCode();
    assertThat("status code is incorrect : " +
        response.getBody(), currentStatusCode.value(), is(statusCode));
  }

  @When("the client calls GET {string} with {string} and {string}")
  public void theClientCallsGETApiVTransactionStatusReferenceWithAccountAndOrder(String uri,
      String value1, String value2) {
    response = template.getForEntity(uri + value1 + value2, Object.class);
  }

  @When("the client calls POST {string} with body")
  public void theClientCallsPOSTApiVTransactionWithBody(String uri) {
    HttpEntity requestEntity = new HttpEntity(
        getTransactionToday(UUID.randomUUID().toString().substring(1, 6)),
        getHeaders());

    response = template.postForEntity(uri, requestEntity, Object.class);
  }

  @When("the client calls POST {string} with string body {string}")
  public void theClientCallsPOSTApiVTransactionWithStringBodyA(String uri, String body) {
    HttpEntity requestEntity = new HttpEntity(body, getHeaders());

    response = template.postForEntity(uri, requestEntity, Object.class);
  }
}
