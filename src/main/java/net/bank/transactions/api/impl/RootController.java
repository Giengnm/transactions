package net.bank.transactions.api.impl;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RootController {

  @GetMapping("/")
  public Mono<Void> indexController(ServerHttpResponse response) {
    response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
    response.getHeaders().setLocation(URI.create("/swagger-ui.html"));
    return response.setComplete();
  }
}
