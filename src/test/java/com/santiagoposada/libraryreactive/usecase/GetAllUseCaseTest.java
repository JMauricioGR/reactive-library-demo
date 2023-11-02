package com.santiagoposada.libraryreactive.usecase;

import com.santiagoposada.libraryreactive.repository.ResourceRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class GetAllUseCaseTest {

    @MockBean
    private ResourceRepository resourceRepository;

    @SpyBean
    private GetAllUseCase getAllUseCase;

}
