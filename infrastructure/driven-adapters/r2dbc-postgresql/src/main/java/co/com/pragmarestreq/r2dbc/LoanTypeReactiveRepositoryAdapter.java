package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.loan_type.Loan_type;
import co.com.pragmarestreq.model.loan_type.gateways.Loan_typeRepository;
import co.com.pragmarestreq.model.state.gateways.StateRepository;
import co.com.pragmarestreq.r2dbc.entity.Loan_typeData;
import co.com.pragmarestreq.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter   extends ReactiveAdapterOperations<
        Loan_type/* change for domain model */,
        Loan_typeData/* change for adapter model */,
        Integer,
        LoanTypeReactiveRepository
        > implements Loan_typeRepository {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Loan_type.class/* change for domain model */));
    }

    @Transactional
    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }

}