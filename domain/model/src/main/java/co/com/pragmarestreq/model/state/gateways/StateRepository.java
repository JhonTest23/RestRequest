package co.com.pragmarestreq.model.state.gateways;

import co.com.pragmarestreq.model.state.State;
import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<Boolean> existsById(Integer id);
    Mono<State> findByIdEstado(Integer idEstado);
}
