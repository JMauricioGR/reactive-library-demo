package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.usecase.CreateResourceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
public class CreateRouteTest {

    @MockBean
    private CreateResourceUseCase createResourceUseCase;

    @Test
    @DisplayName("Create resource route")
    void createResourceRouteTest(){

        // Arrange
        ResourceDTO resource = new ResourceDTO();

        resource.setId("aaa111bbb");
        resource.setName("Jorge Acevedo");
        resource.setCategory("Biological");
        resource.setType("Type 1");
        resource.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource.setUnitsOwed(2);
        resource.setUnitsAvailable(8);

        // Act
        Mockito.when(createResourceUseCase.apply(resource))
                .thenReturn(Mono.just(resource));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().createResourceRoute(createResourceUseCase))
                        .build();

        //Assert
        webTestClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(resource), ResourceDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class)
                .value(result ->{
                    StepVerifier.create(createResourceUseCase.apply(resource))
                            .expectNext(resource)
                            .expectComplete()
                            .verify();
                });
    }
}
