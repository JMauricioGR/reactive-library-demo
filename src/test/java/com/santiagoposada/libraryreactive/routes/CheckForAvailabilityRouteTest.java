package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.CheckAvailabilityUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class CheckForAvailabilityRouteTest {

    @MockBean
    private CheckAvailabilityUseCase checkAvailabilityUseCase;

    @Test
    @DisplayName("Check for availability route")
    void checkForAvailabilityRouteTest(){

        //Arrange
        ResourceDTO resource = new ResourceDTO();
        resource.setId("11223344");
        resource.setName("Chemistry");

        //Act
        Mockito.when(checkAvailabilityUseCase.apply(resource.getId()))
                .thenReturn(Mono.just(resource.getName() + " is available"));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().checkForAvailabilityRoute(checkAvailabilityUseCase))
                .build();

        //Assert
        webTestClient.get()
                .uri("/availability/" + resource.getId())
                .exchange()
                .expectBody(String.class)
                .value(result -> {
                    StepVerifier.create(checkAvailabilityUseCase.apply(resource.getId()))
                            .expectNext(resource.getName() + " is available")
                            .expectComplete()
                            .verify();
                });
    }
}
