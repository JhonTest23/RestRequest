package co.com.pragmarestreq.model.user.gateways;

import co.com.pragmarestreq.model.user.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository {
    Mono<List<User>> getUsersByEmails(List<String> emails);
}
