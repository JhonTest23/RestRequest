package co.com.pragmarestreq.model.requestform.gateways;

import co.com.pragmarestreq.model.requestform.RequestForm;
import reactor.core.publisher.Mono;

public interface RequestFormRepository {
    Mono<RequestForm> save(RequestForm request);
}
