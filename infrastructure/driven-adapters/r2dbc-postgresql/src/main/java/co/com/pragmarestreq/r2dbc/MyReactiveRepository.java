package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.r2dbc.entity.RequestFormData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository  extends ReactiveCrudRepository<RequestFormData, Long>, ReactiveQueryByExampleExecutor<RequestFormData> {
}
