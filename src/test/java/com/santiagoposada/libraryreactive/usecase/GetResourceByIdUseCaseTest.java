package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.dto.ResourceDTO;
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
public class GetResourceByIdUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @SpyBean
    private GetResourceByIdUseCase getResourceByIdUseCase;

    @Test
    @DisplayName("Get resource by Id")
    void getByIdUseCaseTest(){

        // Arrange
        Resource resource = new Resource();

        resource.setId("aaa111bbb");
        resource.setName("Evolution Theory");
        resource.setCategory("Biological");
        resource.setType("Type 1");
        resource.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource.setUnitsOwed(2);
        resource.setUnitsAvailable(8);

        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId("aaa111bbb");
        resourceDTO.setName("Evolution Theory");
        resourceDTO.setCategory("Biological");
        resourceDTO.setType("Type 1");
        resourceDTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resourceDTO.setUnitsOwed(2);
        resourceDTO.setUnitsAvailable(8);


        // Act
        Mockito.when(resourceRepository.findById(resourceDTO.getId()))
                .thenReturn(Mono.just(resource));

        // Assert
        Mono<ResourceDTO> result = getResourceByIdUseCase.apply(resourceDTO.getId());

        StepVerifier.create(result)
                .expectNext(resourceDTO)
                .expectComplete()
                .verify();

    }
}
