package co.com.pragmarestreq.model.jwtoken;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestFormReport {
    private Double monto;
    private Integer plazo;
    private String email;
    private String nombre;
    private String tipo_prestamo;
    private Double tasa_interes;
    private String estado_solicitud;
    private Double salarioBase;
    private Double deuda_total_mensual_solicitudes_aprobadas;
}
