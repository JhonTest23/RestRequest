package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RequestFormUseCase {
//    private RequestFormRepository requestRepository;
//
//    public RequestFormUseCase(RequestFormRepository requestRepository) {
//        this.requestRepository = requestRepository;
//    }
//
//    public Mono<RequestForm> saveRequest(RequestForm request) {
//        return requestRepository.save(request);
//    }

    private final RequestFormRepository requestFormRepository;
    private final Loan_typeRepository loanTypeRepository;
    private final StateRepository stateRepository;

    public Mono<RequestForm> saveRequest(RequestForm requestForm) {

        requestForm.setIdEstado(1); //Se agrega por defecto el estado Pendiente de revisión
        return Mono.zip(
                loanTypeRepository.existsById(requestForm.getIdTipoPrestamo()),
                stateRepository.existsById(requestForm.getIdEstado())
        ).flatMap(tuple -> {
            boolean loanTypeExists = tuple.getT1();
//            boolean stateExists = tuple.getT2();
//            Double monto = requestForm.getMonto();
            if (!loanTypeExists) { return Mono.error(new Exception("Tipo de prestamo no existe")); }
//            if (!stateExists) { return Mono.error(new Exception("Estado no existe")); }
            return requestFormRepository.save(requestForm);
        });
    }

}
