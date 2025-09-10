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


    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
//    @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
//    public Mono<ObjectResponse> testGet() {
//        return client
//                .get()
//                .retrieve()
//                .bodyToMono(ObjectResponse.class);
//    }

// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

//    @CircuitBreaker(name = "testPost")
//    public Mono<ObjectResponse> testPost() {
//        ObjectRequest request = ObjectRequest.builder()
//            .val1("exampleval1")
//            .val2("exampleval2")
//            .build();
//        return client
//                .post()
//                .body(Mono.just(request), ObjectRequest.class)
//                .retrieve()
//                .bodyToMono(ObjectResponse.class);
//    }

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