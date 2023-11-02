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
public class UpdateUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @SpyBean
    private UpdateUseCase updateUseCase;

    @Test
    @DisplayName("Updates resource")
    void updateUseCaseTest(){

        // Arrange
        Resource resource = new Resource();

        resource.setId("aaa111bbb");
        resource.setName("Jorge Acevedo");
        resource.setCategory("Biological");
        resource.setType("Type 1");
        resource.setLastBorrow(LocalDate.parse("2023-11-02"));
        resource.setUnitsOwed(2);
        resource.setUnitsAvailable(8);

        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setId(resource.getId());
        resourceDTO.setName(resource.getName());
        resourceDTO.setCategory(resource.getCategory());
        resourceDTO.setType(resource.getType());
        resourceDTO.setLastBorrow(resource.getLastBorrow());
        resourceDTO.setUnitsOwed(resource.getUnitsOwed());
        resourceDTO.setUnitsAvailable(resource.getUnitsAvailable());


        // Act
        Mockito.when(resourceRepository.save(resource))
                .thenReturn(Mono.just(resource));

        // Assert

        Mono<ResourceDTO> result = updateUseCase.apply(resourceDTO);

        StepVerifier.create(result)
                .expectNext(resourceDTO)
                .expectComplete()
                .verify();

    }

}
