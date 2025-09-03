package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.r2dbc.entity.RequestFormData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository  extends ReactiveCrudRepository<RequestFormData, Integer>, ReactiveQueryByExampleExecutor<RequestFormData> {

    @Query("SELECT COUNT(*) FROM solicitud")
    Mono<Long> countAllRequestForm();

    @Query("SELECT * FROM solicitud ORDER BY id_solicitud OFFSET :page * :size LIMIT :size")
    Flux<RequestFormData> findAllRequestFormsPaged(int size, int offset);
}
