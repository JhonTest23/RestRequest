package co.com.pragmarestreq.model.requestform;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestForm {
    private Integer idSolicitud;
    private Double monto;
    private Integer plazo;
    private String email;
    private Integer idEstado;
    private Integer idTipoPrestamo;
}
