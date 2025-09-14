package co.com.pragmarestreq.consumer;

import co.com.pragmarestreq.model.jwtoken.ReactiveRequestContext;
import co.com.pragmarestreq.model.user.User;
import co.com.pragmarestreq.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserRepository /* implements Gateway from domain */ {
    private final WebClient client;


    @Override
    public Mono<List<User>> getUsersByEmails(List<String> emails) {
        return ReactiveRequestContext.getToken()
                .flatMap(token ->
                        client.post()
                                .uri("/api/v1/usuarios_emails")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .bodyValue(emails)
                                .retrieve()
                                .bodyToFlux(User.class)
                                .collectList()
                );
    }
}