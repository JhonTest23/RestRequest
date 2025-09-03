package co.com.pragmarestreq.model.requestform.gateways;

import co.com.pragmarestreq.model.requestform.RequestForm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestFormRepository {
    Mono<RequestForm> save(RequestForm request);
    Mono<Long> countAllRequestForm();
    Flux<RequestForm> findAllRequestFormsPaged(int size, int offset);
}
