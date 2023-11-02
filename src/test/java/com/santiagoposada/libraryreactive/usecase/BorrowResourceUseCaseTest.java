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
import org.springframework.boot.test.mock.mockito.SpyBeans;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDate;

@SpringBootTest
public class BorrowResourceUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private UpdateUseCase updateUseCase;

    @SpyBean
    private BorrowResourceUseCase borrowResourceUseCase;


    @Test
    @DisplayName("Borrow resource")
    void borrowResourceTest() {

        //Arrange
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
        resourceUpdated.setUnitsOwed(resource.getUnitsOwed() + 1);
        resourceUpdated.setUnitsAvailable(resource.getUnitsAvailable() - 1);


        //Act
        Mockito.when(resourceRepository.findById(resourceDTO.getId())).thenReturn(Mono.just(resource));

        Mockito.when(updateUseCase.apply(any())).thenReturn(Mono.just(resourceUpdated));

        //Assert
        var result = borrowResourceUseCase.apply(resource.getId());

        result.block().equals(resourceUpdated);
    }
}






