package co.com.pragmarestreq.model.loan_type;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan_type {
    public Integer idTipoPrestamo;
    public String nombre;
    public Double montoMinimo;
    public Double montoMaximo;
    public Double tasaInteres;
    public String validacionAutomatica;
}
