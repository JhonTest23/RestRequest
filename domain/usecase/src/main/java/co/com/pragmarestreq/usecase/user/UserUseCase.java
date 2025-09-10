package co.com.pragmarestreq.usecase.user;

import co.com.pragmarestreq.model.user.User;
import co.com.pragmarestreq.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository externalUserGateway;

    public Mono<List<User>> fetchUsersByEmails(List<String> emails) {
        return externalUserGateway.getUsersByEmails(emails);
    }
}
