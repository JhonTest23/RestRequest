    package co.com.pragmarestreq.model.jwtoken;

    import reactor.core.publisher.Mono;


    public class ReactiveRequestContext {
        public static Mono<String> getToken() {
            return Mono.deferContextual(ctx -> Mono.just(ctx.get("token")));
        }
    }