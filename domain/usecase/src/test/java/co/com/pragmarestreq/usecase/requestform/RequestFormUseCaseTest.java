package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.jwtoken.RequestFormReport;
import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.model.state.State;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import co.com.pragmarestreq.model.user.User;
import co.com.pragmarestreq.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequestFormUseCaseTest {

    private RequestFormRepository requestFormRepository;
    private Loan_typeRepository loanTypeRepository;
    private StateRepository stateRepository;
    private UserRepository userRepository;

    private RequestFormUseCase useCase;

    @BeforeEach
    void setup() {
        requestFormRepository = Mockito.mock(RequestFormRepository.class);
        loanTypeRepository = Mockito.mock(Loan_typeRepository.class);
        stateRepository = Mockito.mock(StateRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        useCase = new RequestFormUseCase(requestFormRepository, loanTypeRepository, stateRepository, userRepository);
    }

    // ✅ Test saveRequest success
    @Test
    void saveRequest_success() {
        RequestForm form = RequestForm.builder()
                .idSolicitud(1)
                .email("test@mail.com")
                .idTipoPrestamo(100)
                .monto(5000.0)
                .plazo(12)
                .build();

        when(loanTypeRepository.existsById(100)).thenReturn(Mono.just(true));
        when(requestFormRepository.save(any(RequestForm.class)))
                .thenReturn(Mono.just(form.toBuilder().idEstado(1).build()));

        StepVerifier.create(useCase.saveRequest(form))
                .expectNextMatches(saved -> saved.getIdEstado() == 1)
                .verifyComplete();

        verify(loanTypeRepository, times(1)).existsById(100);
        verify(requestFormRepository, times(1)).save(any(RequestForm.class));
    }

    // ✅ Test saveRequest error when loan type does not exist
    @Test
    void saveRequest_loanTypeNotFound() {
        RequestForm form = RequestForm.builder()
                .idSolicitud(1)
                .idTipoPrestamo(200)
                .build();

        when(loanTypeRepository.existsById(200)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.saveRequest(form))
                .expectErrorMatches(err -> err.getMessage().equals("Tipo de prestamo no existe"))
                .verify();

        verify(requestFormRepository, never()).save(any());
    }

    // ✅ Test setAllData enriches with user info
    @Test
    void setAllData_success() {
        RequestFormReport report = RequestFormReport.builder()
                .email("user@mail.com")
                .build();

        User user = User.builder()
                .email("user@mail.com")
                .nombres("John")
                .salarioBase(2000.0)
                .build();

        when(userRepository.getUsersByEmails(List.of("user@mail.com")))
                .thenReturn(Mono.just(List.of(user)));

        StepVerifier.create(useCase.setAllData(List.of(report)))
                .expectNextMatches(list ->
                        list.size() == 1 &&
                                list.get(0).getNombre().equals("John") &&
                                list.get(0).getSalarioBase() == 2000.0)
                .verifyComplete();
    }

    // ✅ Test setAllData with no matching users
    @Test
    void setAllData_noUsersFound() {
        RequestFormReport report = RequestFormReport.builder()
                .email("unknown@mail.com")
                .build();

        when(userRepository.getUsersByEmails(List.of("unknown@mail.com")))
                .thenReturn(Mono.just(List.of()));

        StepVerifier.create(useCase.setAllData(List.of(report)))
                .expectNextMatches(list ->
                        list.size() == 1 &&
                                list.get(0).getNombre() == null) // no enrichment
                .verifyComplete();
    }

    // ✅ Test updateRequestFormState success
    @Test
    void updateRequestFormState_success() {
        State state = State.builder()
                .idEstado(2)
                .mover_estado_manual(true)
                .build();

        RequestForm updated = RequestForm.builder()
                .idSolicitud(1)
                .idEstado(2)
                .build();

        when(stateRepository.findByIdEstado(2)).thenReturn(Mono.just(state));
        when(requestFormRepository.updateRequestFormState(1, 2)).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.updateRequestFormState(1, 2))
                .expectNextMatches(form -> form.getIdEstado() == 2)
                .verifyComplete();

        verify(requestFormRepository).updateRequestFormState(1, 2);
    }

    // ✅ Test updateRequestFormState error when state not allowed
    @Test
    void updateRequestFormState_notAllowed() {
        State state = State.builder()
                .idEstado(3)
                .mover_estado_manual(false)
                .build();

        when(stateRepository.findByIdEstado(3)).thenReturn(Mono.just(state));

        StepVerifier.create(useCase.updateRequestFormState(1, 3))
                .expectErrorMatches(err -> err.getMessage().equals("No esta habilitado este estado"))
                .verify();

        verify(requestFormRepository, never()).updateRequestFormState(any(), any());
    }
}
