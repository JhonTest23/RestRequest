package co.com.pragmarestreq.model.requestform;
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
public class RequestForm {
    private long id_solicitud;
    private double monto;
    private int plazo;
    private String email;
    private long id_estado;
    private long id_tipo_prestamo;

    public RequestForm(double monto, int plazo, String email, long id_tipo_prestamo) {
        this.monto = monto;
        this.plazo = plazo;
        this.email = email;
        this.id_tipo_prestamo = id_tipo_prestamo;
        this.id_estado = 1;
    }

    public long getId_solicitud() {
        return id_solicitud;
    }

    public double getMonto() {
        return monto;
    }

    public int getPlazo() {
        return plazo;
    }

    public String getEmail() {
        return email;
    }

    public long getId_estado() {
        return id_estado;
    }

    public long getId_tipo_prestamo() {
        return id_tipo_prestamo;
    }
}
