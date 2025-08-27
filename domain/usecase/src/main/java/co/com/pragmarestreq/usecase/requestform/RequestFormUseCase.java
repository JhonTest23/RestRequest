package co.com.pragmarestreq.usecase.requestform;

import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RequestFormUseCase {
    private RequestFormRepository requestRepository;

    public RequestFormUseCase(RequestFormRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Mono<RequestForm> saveRequest(RequestForm request) {
        return requestRepository.save(request);
    }
}
