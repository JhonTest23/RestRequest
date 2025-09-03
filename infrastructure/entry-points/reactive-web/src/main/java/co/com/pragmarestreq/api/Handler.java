package co.com.pragmarestreq.api;


import co.com.pragmarestreq.model.jwtoken.PaginatedResponse;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.usecase.requestform.RequestFormUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final RequestFormUseCase requestFormUseCase;
    private final RequestFormRepository requestFormRepository;

    public Mono<ServerResponse> saveRequestForm(ServerRequest request) {
        return request.bodyToMono(RequestForm.class)
                .flatMap(requestFormUseCase::saveRequest)
                .flatMap(requestForm -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(requestForm));
    }

    public  Mono<ServerResponse> TestUser(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Bienvenido a la prueba"), String.class);
    }

    public Mono<ServerResponse> getRequestForm(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));

        return requestFormRepository.countAllRequestForm()
                .flatMap(totalElements ->
                        requestFormRepository.findAllRequestFormsPaged(page, size)
                                .collectList()
                                .flatMap(requestForms -> {
                                    PaginatedResponse<?> response =
                                            new PaginatedResponse<>(requestForms, page, size, totalElements);
                                    return ServerResponse.ok().bodyValue(response); // ✅ correct
                                })
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
