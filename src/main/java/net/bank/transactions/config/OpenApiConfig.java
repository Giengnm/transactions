package net.bank.transactions.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("Contact Application API")
            .description(
                "This is a sample Spring Boot RESTFull service using springdoc-openapi and OpenAPI 3.")
        );
  }

  @Bean
  public GroupedOpenApi groupOpenApi() {

    String[] paths = {"/**"};
    return GroupedOpenApi
        .builder()
        .group("all")
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi groupOpenApiV1() {
    String[] paths = {"/api/v1/**"};
    return GroupedOpenApi
        .builder()
        .group("groups-v1")
        .pathsToMatch(paths)
        .build();
  }

}