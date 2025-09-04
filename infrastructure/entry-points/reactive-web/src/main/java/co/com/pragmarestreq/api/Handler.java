package co.com.pragmarestreq.api;


import co.com.pragmarestreq.api.Jwt.JwtUtil;
import co.com.pragmarestreq.model.jwtoken.PaginatedResponse;
import co.com.pragmarestreq.model.jwtoken.RequestFormReport;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.usecase.requestform.RequestFormUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final JwtUtil jwtUtil;

    public Mono<ServerResponse> saveRequestForm(ServerRequest request) {
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION).replace("Bearer","").replace(" ","");

        if (!jwtUtil.validateToken(token)) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue("Validar el token agregado");
        }

        String emailUser = jwtUtil.extractEmail(token);

        return request.bodyToMono(RequestForm.class)
                .flatMap(requestForm ->{
                    if (!requestForm.getEmail().equals(emailUser)) {
                        return ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue("Email in request does not match JWT token");
                    }

                    return requestFormUseCase.saveRequest(requestForm)
                              .flatMap(savedRequest -> ServerResponse.ok()
                              .contentType(MediaType.APPLICATION_JSON)
                                      .bodyValue(savedRequest));
                });
    }

    public  Mono<ServerResponse> TestUser(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Bienvenido a la prueba"), String.class);
    }

//    public Mono<ServerResponse> getRequestForm(ServerRequest request) {
//        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
//        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
//
//        return requestFormRepository.countAllRequestForm()
//                .flatMap(totalElements ->
//                        requestFormRepository.findAllRequestFormsPaged(page, size)
//                                .collectList()
//                                .flatMap(requestForms -> {
//                                    PaginatedResponse<?> response =
//                                            new PaginatedResponse<>(requestForms, page, size, totalElements);
//                                    return ServerResponse.ok().bodyValue(response); // ✅ correct
//                                })
//                )
//                .switchIfEmpty(ServerResponse.notFound().build());
//    }

    public Mono<ServerResponse> getRequestForm(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        int offset = page * size;

        return requestFormRepository.countAllRequestForm()
                .flatMap(totalElements ->
                        requestFormRepository.findAllRequestFormsPaged(size, offset)
                                .collectList()
                                .flatMap(requestForms -> {
                                    PaginatedResponse<RequestFormReport> response =
                                            new PaginatedResponse<>(requestForms, page, size, totalElements);
                                    return ServerResponse.ok().bodyValue(response);
                                })
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
