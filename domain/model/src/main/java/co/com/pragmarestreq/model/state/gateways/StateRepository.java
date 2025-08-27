package co.com.pragmarestreq.model.state.gateways;

import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<Boolean> existsById(Integer id);
}
