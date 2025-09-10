package co.com.pragmarestreq.api;


import co.com.pragmarestreq.api.jwtokenconfig.JwtUtil;
import co.com.pragmarestreq.model.jwtoken.PaginatedResponse;
import co.com.pragmarestreq.model.jwtoken.RequestFormReport;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.model.requestform.UpdateRequestFormState;
import co.com.pragmarestreq.model.requestform.gateways.RequestFormRepository;
import co.com.pragmarestreq.usecase.requestform.RequestFormUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final RequestFormUseCase requestFormUseCase;
    private final RequestFormRepository requestFormRepository;
    private final JwtUtil jwtUtil;

    public Mono<ServerResponse> saveRequestForm(ServerRequest request) {
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION).replace("Bearer","").replace(" ","");
        log.info("Incoming saveRequestForm request with token: {}", token);
        if (!jwtUtil.validateToken(token)) {
            log.warn("Invalid JWT token");
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue("Validar el token agregado");
        }

        String emailUser = jwtUtil.extractEmail(token);
        log.debug("✅ Extracted email from JWT: {}", emailUser);

        return request.bodyToMono(RequestForm.class)
                .flatMap(requestForm ->{
                    if (!requestForm.getEmail().equals(emailUser)) {
                        log.warn("Email mismatch: request={}, token={}", requestForm.getEmail(), emailUser);
                        return ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue("Email in request does not match JWT token");
                    }

                    return requestFormUseCase.saveRequest(requestForm)
                            .doOnSuccess(saved -> log.info("Saved requestForm with id={}", saved.getIdSolicitud()))
                            .doOnError(err -> log.error("Error saving requestForm", err))
                            .flatMap(savedRequest -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                                      .bodyValue(savedRequest));
                });
    }

    public Mono<ServerResponse> TestUser(ServerRequest request) {
        log.info("👤 TestUser endpoint hit");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Bienvenido a la prueba"), String.class);
    }

    public Mono<ServerResponse> getRequestForm(ServerRequest request) {
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION).replace("Bearer","").replace(" ","");
        log.info("Incoming getRequestForm request, token={}", token);

        if (!jwtUtil.validateToken(token)) {
            log.warn("Invalid JWT token");
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue("Validar el token agregado");
        }

        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        int offset = page * size;
        log.debug("Pagination params: page={}, size={}, offset={}", page, size, offset);

        return requestFormRepository.countAllRequestForm()
                .doOnNext(count -> log.info("Total request forms: {}", count))
                .flatMap(totalElements ->
                        requestFormRepository.findAllRequestFormsPaged(size, offset)
                                .collectList()
                                .doOnNext(list -> log.debug("Retrieved {} requestForms", list.size()))
                                .flatMap(requestFormUseCase::setAllData)
                                .flatMap(requestForms -> {
                                    log.info("Returning {} enriched requestForms", requestForms.size());
                                    PaginatedResponse<RequestFormReport> response =
                                            new PaginatedResponse<>(requestForms, page, size, totalElements);
                                    return ServerResponse.ok().bodyValue(response);
                                })
                )
                .doOnError(err -> log.error("Error in getRequestForm", err))
                .switchIfEmpty(ServerResponse.notFound().build())
                .contextWrite(ctx -> ctx.put("token", token));
    }

    public Mono<ServerResponse> updateRequestFormState(ServerRequest request) {
        log.info("Incoming updateRequestFormState request");

        return request.bodyToMono(UpdateRequestFormState.class)
                .doOnNext(dto -> log.debug("DTO received: {}", dto))
                .flatMap(dto -> requestFormUseCase.updateRequestFormState(dto.getIdAlias(), dto.getIdEstado()))
                .doOnSuccess(updated -> log.info("Updated RequestForm with idSolicitud={}, new estado={}",
                        updated.getIdSolicitud(), updated.getIdEstado()))
                .doOnError(err -> log.error("Error updating requestForm state", err))
                .flatMap(updated -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updated))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
