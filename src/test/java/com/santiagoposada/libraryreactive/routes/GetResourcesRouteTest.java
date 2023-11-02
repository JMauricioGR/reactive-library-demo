package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.usecase.GetAllUseCase;
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
public class GetResourcesRouteTest {

    @MockBean
    private GetAllUseCase getAllUseCase;

    @Test
    @DisplayName("Get all resources route")
    void getAllResourcesRouteTest(){

        //Arrange
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId("aaa111bbb");
        resourceDTO.setName("Evolution Theory");
        resourceDTO.setCategory("Biological");
        resourceDTO.setType("Type 1");
        resourceDTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceDTO.setUnitsOwed(2);
        resourceDTO.setUnitsAvailable(8);

        ResourceDTO resource2DTO = new ResourceDTO();

        resource2DTO.setId("ccc222ddd");
        resource2DTO.setName("Cinematic");
        resource2DTO.setCategory("Physics");
        resource2DTO.setType("Type 2");
        resource2DTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource2DTO.setUnitsOwed(1);
        resource2DTO.setUnitsAvailable(9);

        Flux<ResourceDTO> resourceResponse = Flux.just(resourceDTO, resource2DTO);

        // Act
        Mockito.when(getAllUseCase.get())
                .thenReturn(resourceResponse);

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().getAllRouter(getAllUseCase))
                .build();

        // Assert
        webTestClient.get()
                .uri("/resources")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResourceDTO.class)
                .value(result ->{
                    StepVerifier.create(getAllUseCase.get())
                            .expectNext(resourceDTO)
                            .expectNext(resource2DTO)
                            .expectComplete()
                            .verify();
                });
    }
}
