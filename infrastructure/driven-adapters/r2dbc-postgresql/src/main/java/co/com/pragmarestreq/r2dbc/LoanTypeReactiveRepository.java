package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.r2dbc.entity.Loan_typeData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanTypeReactiveRepository extends ReactiveCrudRepository<Loan_typeData, Integer>, ReactiveQueryByExampleExecutor<Loan_typeData> {
}


