package net.bank.transactions.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import net.bank.transactions.TransactionsApplication;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;


@CucumberContextConfiguration
@SpringBootTest(classes = {TransactionsApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TransactionsApplication.class, loader = SpringBootContextLoader.class)
@Slf4j
public class AbstractDefinitions {

  @Autowired
  protected TestRestTemplate template;

  protected ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() {
    log.info("**********Cucumber started **********");
  }
}
