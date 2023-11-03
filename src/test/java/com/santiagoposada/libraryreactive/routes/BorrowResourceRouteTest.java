package com.santiagoposada.libraryreactive.routes;


import com.santiagoposada.libraryreactive.dto.ResourceDTO;
import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.usecase.BorrowResourceUseCase;
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
public class BorrowResourceRouteTest {

    @MockBean
    private BorrowResourceUseCase borrowResourceUseCase;

    @Test
    @DisplayName("Borrow resource route")
    void borrowResourceRouteTest(){


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
        Mockito.when(borrowResourceUseCase.apply(resourceDTO.getId()))
                .thenReturn(Mono.just("The resource "
                        + resourceDTO.getName() + " has been borrowed, there are "
                        + resourceDTO.getUnitsAvailable() + " units available"));

        var webTestClient = WebTestClient.bindToRouterFunction(new ResourceRouter().borrowResourceRoute(borrowResourceUseCase))
                .build();

        //Assert
        webTestClient.put()
                .uri("/borrow/" + resourceDTO.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(result -> {
                    StepVerifier.create(borrowResourceUseCase.apply(resourceDTO.getId()))
                            .expectNext("The resource "
                                    + resourceDTO.getName() + " has been borrowed, there are "
                                    + resourceDTO.getUnitsAvailable() + " units available")
                            .expectComplete()
                            .verify();
                });
    }
}
