package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequestFormUseCaseTest {

    private RequestFormRepository requestFormRepository;
    private Loan_typeRepository loanTypeRepository;
    private StateRepository stateRepository;
    private RequestFormUseCase requestFormUseCase;

    @BeforeEach
    void setUp() {
        requestFormRepository = Mockito.mock(RequestFormRepository.class);
        loanTypeRepository = Mockito.mock(Loan_typeRepository.class);
        requestFormUseCase = new RequestFormUseCase(requestFormRepository, loanTypeRepository, stateRepository);
     }


    @Test
    void saveRequest_shouldSaveRequest_whenLoanTypeExists() {
        // Arrange
        RequestForm requestForm = new RequestForm();
        requestForm.setIdTipoPrestamo(1);

        when(loanTypeRepository.existsById(1)).thenReturn(Mono.just(true));
        when(requestFormRepository.save(any(RequestForm.class))).thenReturn(Mono.just(requestForm));

        // Act
        Mono<RequestForm> result = requestFormUseCase.saveRequest(requestForm);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(saved -> saved.getIdEstado() == 1 && saved.getIdTipoPrestamo() == 1)
                .verifyComplete();

        verify(loanTypeRepository).existsById(1);
        verify(requestFormRepository).save(any(RequestForm.class));
    }

    @Test
    void saveRequest_shouldReturnError_whenLoanTypeDoesNotExist() {
        // Arrange
        RequestForm requestForm = new RequestForm();
        requestForm.setIdTipoPrestamo(99);

        when(loanTypeRepository.existsById(99)).thenReturn(Mono.just(false));

        // Act
        Mono<RequestForm> result = requestFormUseCase.saveRequest(requestForm);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Tipo de prestamo no existe"))
                .verify();

        verify(loanTypeRepository).existsById(99);
        verify(requestFormRepository, never()).save(any());
    }
}
