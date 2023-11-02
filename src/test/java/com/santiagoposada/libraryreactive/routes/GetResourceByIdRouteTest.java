package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.GetResourceByIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
public class GetResourceByIdRouteTest {

    @MockBean
    private GetResourceByIdUseCase getResourceByIdUseCase;

    @Test
    @DisplayName("Get resource by Id route")
    void getResourceByIdRouteTest(){

        //Arrange
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId("aaa111bbb");
        resourceDTO.setName("Evolution Theory");
        resourceDTO.setCategory("Biological");
        resourceDTO.setType("Type 1");
        resourceDTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceDTO.setUnitsOwed(2);
        resourceDTO.setUnitsAvailable(8);

        // Act
        Mockito.when(getResourceByIdUseCase.apply(resourceDTO.getId()))
                .thenReturn(Mono.just(resourceDTO));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().getResourceById(getResourceByIdUseCase))
                .build();

        webTestClient.get()
                .uri("/resource/" + resourceDTO.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResourceDTO.class)
                .value(result ->{
                    StepVerifier.create(getResourceByIdUseCase.apply(resourceDTO.getId()))
                            .expectNext(resourceDTO)
                            .expectComplete()
                            .verify();
                });

    }

}
