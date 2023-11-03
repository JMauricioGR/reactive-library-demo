package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.DeleteResourceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class DeleteResourceRouteTest {

    @MockBean
    private DeleteResourceUseCase deleteResourceUseCase;

    @Test
    @DisplayName("Delete resource route")
    void deleteResourceRouteTest(){

        //Arrange
        ResourceDTO resource = new ResourceDTO();
        resource.setId("12345");

        //Act
        Mockito.when(deleteResourceUseCase.apply(resource.getId()))
                .thenReturn(Mono.empty());

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().deleteResourceToute(deleteResourceUseCase))
                .build();

        //Assert
        webTestClient.delete()
                .uri("/delete/" + resource.getId())
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(Void.class)
                .value(result -> {
                    StepVerifier.create(deleteResourceUseCase.apply(resource.getId()))
                            .expectNextCount(0)
                            .expectComplete()
                            .verify();
                });
    }

}
