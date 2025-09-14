package co.com.pragmarestreq.api;

import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.usecase.requestform.RequestFormUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/solicitud",     // <-- link to the actual route
                    method = RequestMethod.POST,              // <-- HTTP method
                    beanClass = Handler.class,                // <-- your handler class
                    beanMethod = "saveRequestForm",
                    operation = @Operation(
                            operationId = "saveRequestForm",
                            summary = "Save a form request",
                            tags = {"Request form"},
                            requestBody = @RequestBody(      // <-- here is the magic
                                    description = "JSON body with request form data",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = RequestForm.class)
                                    )
                            ),
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "request", description = "Request Form")},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Handler.class))),
                                    @ApiResponse(responseCode = "500", description = "Invalid request form")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/solicitud",     // <-- link to the actual route
                    method = RequestMethod.GET,              // <-- HTTP method
                    beanClass = Handler.class,                // <-- your handler class
                    beanMethod = "getRequestForm",
                    operation = @Operation(
                            operationId = "getRequestForm",
                            summary = "Get the list of form request",
                            tags = {"Request form"},
//                            requestBody = @RequestBody(      // <-- here is the magic
//                                    description = "JSON body with request form data",
//                                    required = true,
//                                    content = @Content(
//                                            schema = @Schema(implementation = RequestForm.class)
//                                    )
//                            ),
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "request", description = "Request Form")},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Handler.class))),
                                    @ApiResponse(responseCode = "500", description = "Invalid request form")
                            }
                    )
            ),
            @RouterOperation(path = "/api/v1/solicitud", beanClass = Handler.class, beanMethod = "updateRequestFormState")
    })
    public RouterFunction<ServerResponse> userRoutes(Handler handler) {
        return route(POST("/api/v1/solicitud"), handler::saveRequestForm)
                .andRoute(GET("/api/v1/solicitud"), handler::getRequestForm)
                .andRoute(PUT("/api/v1/solicitud"), handler::updateRequestFormState);
    }
}