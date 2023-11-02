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

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ReturnUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private UpdateUseCase updateUseCase;

    @SpyBean
    private ReturnUseCase returnUseCase;

    @Test
    @DisplayName("Return resource")
    void returnUseCAseTest(){

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

        ResourceDTO resourceUpdated = new ResourceDTO();
        resourceUpdated.setId(resource.getId());
        resourceUpdated.setName(resource.getName());
        resourceUpdated.setCategory(resource.getCategory());
        resourceUpdated.setType(resource.getType());
        resourceUpdated.setLastBorrow(LocalDate.now());
        resourceUpdated.setUnitsOwed(resource.getUnitsOwed() - 1);
        resourceUpdated.setUnitsAvailable(resource.getUnitsAvailable() + 1);

        //Act
        Mockito.when(resourceRepository.findById(resourceDTO.getId())).thenReturn(Mono.just(resource));

        Mockito.when(updateUseCase.apply(any())).thenReturn(Mono.just(resourceUpdated));

        //Assert
        Mono<String> result = returnUseCase.apply(resource.getId());

        StepVerifier.create(result)
                .expectNext("The resource with id: "
                        + resource.getId() + "was returned successfully")
                .expectComplete()
                .verify();
    }

}
