package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.state.State;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import co.com.pragmarestreq.r2dbc.entity.StateData;
import co.com.pragmarestreq.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class StateReactiveRepositoryAdapter  extends ReactiveAdapterOperations<
        State/* change for domain model */,
        StateData/* change for adapter model */,
        Integer,
        stateReactiveRepository
        > implements StateRepository {
    public StateReactiveRepositoryAdapter(stateReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, State.class/* change for domain model */));
    }

    @Transactional
    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }

}
