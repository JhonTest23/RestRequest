package co.com.pragmarestreq.r2dbc.entity;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.Email;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud") // your PostgreSQL table name
public class RequestFormData {
    @Id
    @Column("id_solicitud")
    private Integer idSolicitud;
    private Double monto;
    private Integer plazo;
    @Email(message = "Correo electrónico inválido")
    @Column("email")
    private String email;
    @Column("id_estado")
    private Integer idEstado;
    @Column("id_tipo_prestamo")
    private Integer idTipoPrestamo;

}
