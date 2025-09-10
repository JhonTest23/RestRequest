package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.jwtoken.RequestFormReport;
import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import co.com.pragmarestreq.model.user.User;
import co.com.pragmarestreq.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RequestFormUseCase {


    private final RequestFormRepository requestFormRepository;
    private final Loan_typeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final UserRepository externalUserGateway;

    public Mono<RequestForm> saveRequest(RequestForm requestForm) {

        return Mono.just("execute").flatMap(x -> loanTypeRepository.existsById(requestForm.getIdTipoPrestamo()))
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new Exception("Tipo de prestamo no existe"));
                    } else {
                        return requestFormRepository.save(defaultStateRequest(requestForm));
                    }
                });
    }



    public Mono<List<RequestFormReport>> setAllData(List<RequestFormReport> requestFormReportList){
        return Mono.just(requestFormReportList)
                .flatMap(reports -> {
                    List<String> emails = reports.stream()
                            .map(RequestFormReport::getEmail)
                            .toList();

                    return externalUserGateway.getUsersByEmails(emails)
                            .map(users -> enrichReportsWithUserData(reports, users));
                });
    }

    protected RequestForm defaultStateRequest(RequestForm requestForm){
        return  RequestForm.builder()
                .idSolicitud(requestForm.getIdSolicitud())
                .monto(requestForm.getMonto())
                .plazo(requestForm.getPlazo())
                .email(requestForm.getEmail())
                .idEstado(1)//Se agrega por defecto el estado Pendiente de revisión
                .idTipoPrestamo(requestForm.getIdTipoPrestamo())
                .build();
    }


    private List<RequestFormReport> enrichReportsWithUserData(
            List<RequestFormReport> reports,
            List<User> users) {

        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getEmail, Function.identity()));

        return reports.stream()
                .map(report -> {
                    User user = userMap.get(report.getEmail());
                    if (user != null) {
                        return report.toBuilder()
                                .nombre(user.getNombres())
                                .salarioBase(user.getSalarioBase())
                                .build();
                    }
                    return report;
                })
                .toList();
    }

    public Mono<RequestForm> updateRequestFormState(Integer idSolicitud, Integer idEstado) {
        return Mono.just(idEstado)
                .flatMap(stateRepository::findByIdEstado)
//                .flatMap(estado -> stateRepository.findByIdEstado(idEstado))
                .flatMap(state -> {
                    if(state.getMover_estado_manual().equals(true)){
                        return requestFormRepository.updateRequestFormState(idSolicitud, idEstado);
                    }
                    else {
                        return Mono.error(new Exception("No esta habilitado este estado"));
                    }
                });
    }
}
