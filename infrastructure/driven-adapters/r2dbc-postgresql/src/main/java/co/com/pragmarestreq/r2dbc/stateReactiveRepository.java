package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.r2dbc.entity.StateData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface stateReactiveRepository extends ReactiveCrudRepository<StateData, Integer>, ReactiveQueryByExampleExecutor<StateData> {
}
