package co.com.pragmarestreq.r2dbc;

import co.com.pragmarestreq.model.jwtoken.RequestFormReport;
import co.com.pragmarestreq.model.requestform.RequestForm;
import co.com.pragmarestreq.r2dbc.entity.RequestFormData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository  extends ReactiveCrudRepository<RequestFormData, Integer>, ReactiveQueryByExampleExecutor<RequestFormData> {

    @Query("SELECT COUNT(*) FROM solicitud")
    Mono<Long> countAllRequestForm();

    @Query("""
            SELECT r.id_solicitud as idalias,
                   r.monto as monto, r.plazo  as plazo, r.email  as email,
                   l.nombre as tipo_prestamo, l.tasa_interes as tasa_interes,
                   s.nombre as estado_solicitud, 0 as deuda_total_mensual_solicitudes_aprobadas
            FROM solicitud r
            JOIN tipo_prestamo l ON r.id_tipo_prestamo = l.id_tipo_prestamo
            JOIN estado s ON r.id_estado = s.id_estado
            WHERE r.id_estado <> 2
            LIMIT :size OFFSET :offset
            """)
    Flux<RequestFormReport> findAllRequestFormsPaged(int size, int offset);
}
