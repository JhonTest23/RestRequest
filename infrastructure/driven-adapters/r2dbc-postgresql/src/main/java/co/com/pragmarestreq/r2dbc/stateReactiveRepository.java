package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.r2dbc.entity.StateData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface stateReactiveRepository extends ReactiveCrudRepository<StateData, Integer>, ReactiveQueryByExampleExecutor<StateData> {
    @Query("SELECT * FROM estado WHERE id_estado = :idEstado")
    Mono<StateData> findByIdEstado(Integer idEstado);
}
