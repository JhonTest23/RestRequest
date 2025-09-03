package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.r2dbc.entity.RequestFormData;
import co.com.pragmarestreq.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        RequestForm/* change for domain model */,
        RequestFormData/* change for adapter model */,
        Integer,
        MyReactiveRepository
        > implements RequestFormRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, RequestForm.class/* change for domain model */));
    }

    @Transactional
    @Override
    public Mono<RequestForm> save(RequestForm requestForm) {
        return super.save(requestForm);
    }

    @Override
    public Mono<Long> countAllRequestForm() {
        return repository.countAllRequestForm();
    }

    @Override
    public Flux<RequestForm> findAllRequestFormsPaged(int size, int offset) {
        return repository.findAllRequestFormsPaged(size, offset)
                .map(RequestFormData::toDomain); // map DB entity to domain model
    }

}
