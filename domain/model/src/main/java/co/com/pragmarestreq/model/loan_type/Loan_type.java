package co.com.pragmarestreq.model.loan_type;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan_type {
    public long id_tipo_prestamo;
    public String nombre;
    public double monto_minimo;
    public double monto_maximo;
    public double tasa_interes;
    public String validacion_automatica;

    public Loan_type(String nombre, double monto_minimo, double monto_maximo, double tasa_interes, String validacion_automatica) {
        this.nombre = nombre;
        this.monto_minimo = monto_minimo;
        this.monto_maximo = monto_maximo;
        this.tasa_interes = tasa_interes;
        this.validacion_automatica = validacion_automatica;
    }
}
