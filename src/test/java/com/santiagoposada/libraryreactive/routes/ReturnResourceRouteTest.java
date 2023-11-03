package com.santiagoposada.libraryreactive.routes;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.usecase.ReturnUseCase;
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
public class ReturnResourceRouteTest {

    @MockBean
    private ReturnUseCase returnUseCase;

    @Test
    @DisplayName("Return resource route")
    public void returnResourceRouteTest() {

        //Arrange
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId("aaa111bbb");
        resourceDTO.setName("Jorge Acevedo");
        resourceDTO.setCategory("Biological");
        resourceDTO.setType("Type 1");
        resourceDTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceDTO.setUnitsOwed(2);
        resourceDTO.setUnitsAvailable(8);

        ResourceDTO resourceResponse = new ResourceDTO();

        resourceResponse.setId("aaa111bbb");
        resourceResponse.setName("Jorge Acevedo");
        resourceResponse.setCategory("Biological");
        resourceResponse.setType("Type 1");
        resourceResponse.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceResponse.setUnitsOwed(resourceDTO.getUnitsOwed() - 1);
        resourceResponse.setUnitsAvailable(resourceDTO.getUnitsAvailable() + 1);

        //Act
        Mockito.when(returnUseCase.apply(resourceDTO.getId()))
                .thenReturn(Mono.just("The resource with id: " + resourceResponse.getId() + "was returned successfully"));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().returnRoute(returnUseCase))
                .build();

        //Assert
        webTestClient.put()
                .uri("/return/" + resourceDTO.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(result -> {
                    StepVerifier.create(returnUseCase.apply(resourceDTO.getId()))
                            .expectNext("The resource with id: " + resourceResponse.getId() + "was returned successfully")
                            .expectComplete()
                            .verify();
                });

    }
}
