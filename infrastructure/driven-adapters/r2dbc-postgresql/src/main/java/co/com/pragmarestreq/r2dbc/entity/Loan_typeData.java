package co.com.pragmarestreq.r2dbc.entity;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipo_prestamo")
public class Loan_typeData {
    @Id
    @Column("id_tipo_prestamo")
    private Integer idTipoPrestamo;
    private String nombre;
    @Column("monto_minimo")
    private Double montoMinimo;
    @Column("monto_maximo")
    private Double montoMaximo;
    @Column("tasa_interes")
    private Double tasaInteres;
    @Column("validacion_automatica")
    private String validacionAutomatica;
}
