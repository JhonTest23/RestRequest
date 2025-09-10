package co.com.pragmarestreq.usecase.state;

import co.com.pragmarestreq.model.state.State;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StateUseCase {
    private final StateRepository stateRepository;

    public Mono<State> getStateById(Integer idEstado) {
        return stateRepository.findByIdEstado(idEstado);
    }
}
