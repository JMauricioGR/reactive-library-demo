package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.entity.Resource;
import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
@SpringBootTest
public class DeleteResourceUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @SpyBean
    private DeleteResourceUseCase deleteResourceUseCase;


    @Test
    @DisplayName("Delete resource")
    public void testDeleteResource() {

        //Arrange
        Resource resource = new Resource();

        resource.setId("aaa111bbb");
        resource.setName("Jorge Acevedo");
        resource.setCategory("Biological");
        resource.setType("Type 1");
        resource.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource.setUnitsOwed(2);
        resource.setUnitsAvailable(8);

        // Act
        Mockito.when(resourceRepository.deleteById(resource.getId())).thenReturn(Mono.empty());

        // Assert
        Mono<Void> result = deleteResourceUseCase.apply(resource.getId());

        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();

    }
}
