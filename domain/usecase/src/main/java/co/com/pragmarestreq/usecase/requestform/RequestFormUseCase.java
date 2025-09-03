package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RequestFormUseCase {


    private final RequestFormRepository requestFormRepository;
    private final Loan_typeRepository loanTypeRepository;
    private final StateRepository stateRepository;

    public Mono<RequestForm> saveRequest(RequestForm requestForm) {

        requestForm.setIdEstado(1); //Se agrega por defecto el estado Pendiente de revisión
        return Mono.just("start").flatMap(x -> loanTypeRepository.existsById(requestForm.getIdTipoPrestamo()))
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new Exception("Tipo de prestamo no existe"));
                    } else {
                        return requestFormRepository.save(requestForm);
                    }
                });
    }
}
