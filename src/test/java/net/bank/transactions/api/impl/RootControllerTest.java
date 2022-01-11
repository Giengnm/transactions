package net.bank.transactions.api.impl;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RootControllerTest {

  @InjectMocks
  private RootController rootController;

  @Mock
  private ServerHttpResponse response;

  @Mock
  private HttpHeaders headers;

  @Test
  void test() {
    when(response.getHeaders()).thenReturn(headers);
    StepVerifier.create(rootController.indexController(response))
        .expectComplete();
  }
}