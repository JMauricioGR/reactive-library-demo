package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.GetByTypeUseCase;
import com.santiagoposada.libraryreactive.usecase.GetResourceByIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
public class GetResourceByTypeRouteTest {

    @MockBean
    private GetByTypeUseCase getByTypeUseCase;

    @Test
    @DisplayName("Get resource by type route")
    void getGetResourceByIdRouteTest(){

        //Arrange
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId("aaa111bbb");
        resourceDTO.setName("Jorge Acevedo");
        resourceDTO.setCategory("Biological");
        resourceDTO.setType("Type 1");
        resourceDTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceDTO.setUnitsOwed(2);
        resourceDTO.setUnitsAvailable(8);

        //Act
        Mockito.when(getByTypeUseCase.apply(resourceDTO.getType()))
                .thenReturn(Flux.just(resourceDTO));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().getByTypeRoute(getByTypeUseCase))
                .build();

        //Assert
        webTestClient.get()
                .uri("/getByType/" + resourceDTO.getType())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDTO.class)
                .value(resourceDTOS -> {
                    StepVerifier.create(getByTypeUseCase.apply(resourceDTO.getType()))
                            .expectNextMatches(result -> result.getType().equals(resourceDTO.getType()))
                            .expectComplete()
                            .verify();
                });


    }

}
