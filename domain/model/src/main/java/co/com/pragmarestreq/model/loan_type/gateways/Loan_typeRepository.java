package co.com.pragmarestreq.model.loan_type.gateways;

import reactor.core.publisher.Mono;

public interface Loan_typeRepository {
    Mono<Boolean> existsById(Integer id);
}
