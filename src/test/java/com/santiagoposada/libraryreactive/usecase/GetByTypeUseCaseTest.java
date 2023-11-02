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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
public class GetByTypeUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @SpyBean
    private GetByTypeUseCase getByTypeUseCase;

    @Test
    @DisplayName("Get by type")
    void getByTypeUseCaseTest(){

        // Arrange
        Resource resource = new Resource();

        resource.setId("aaa111bbb");
        resource.setName("Evolution Theory");
        resource.setCategory("Biological");
        resource.setType("Type 1");
        resource.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource.setUnitsOwed(2);
        resource.setUnitsAvailable(8);

        Resource resource2 = new Resource();

        resource2.setId("ccc222ddd");
        resource2.setName("Biology 1");
        resource2.setCategory("Biological");
        resource2.setType("Type 2");
        resource2.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource2.setUnitsOwed(1);
        resource2.setUnitsAvailable(9);

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
        resource2DTO.setName("Biology 1");
        resource2DTO.setCategory("Biological");
        resource2DTO.setType("Type 2");
        resource2DTO.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource2DTO.setUnitsOwed(1);
        resource2DTO.setUnitsAvailable(9);

        Flux<Resource> resourceResponse = Flux.just(resource, resource2);

        // Act
        Mockito.when(resourceRepository.findAllByType(resourceDTO.getType()))
                .thenReturn(resourceResponse
                        .filter(result -> result.getType()
                                .equals(resourceDTO.getType())));

        //Assert
        Flux<ResourceDTO> result = getByTypeUseCase.apply(resourceDTO.getType());

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getType().equals(resourceDTO.getType()))
                .expectComplete()
                .verify();
    }
}
