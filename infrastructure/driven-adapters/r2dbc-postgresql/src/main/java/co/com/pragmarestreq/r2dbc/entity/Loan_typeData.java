package co.com.pragmarestreq.r2dbc.entity;

import co.com.pragmarestreq.model.loan_type.Loan_type;
import jakarta.persistence.Id;
import lombok.*;
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
    public long id_tipo_prestamo;
    public String nombre;
    public double monto_minimo;
    public double monto_maximo;
    public double tasa_interes;
    public String validacion_automatica;

    public Loan_type toDomain(){
        return new Loan_type(this.nombre, this.monto_minimo, this.monto_maximo, this.tasa_interes, this.validacion_automatica);
    }
}
