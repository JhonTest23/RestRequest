package co.com.pragmarestreq.r2dbc.entity;

import co.com.pragmarestreq.model.requestform.RequestForm;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud") // your PostgreSQL table name
public class RequestFormData {
    @Id
    private String id_solicitud;
    public double monto;
    public int plazo;
    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column("correo_electronico")
    @NonNull
    public String email;
    public Long id_estado;
    public Long id_tipo_prestamo;

    public RequestFormData(double monto, int plazo, String email, Long id_tipo_prestamo) {
        this.monto = monto;
        this.plazo = plazo;
        this.email = email;
        this.id_tipo_prestamo = id_tipo_prestamo;
        this.id_estado = 1L;
    }

    public  static RequestFormData fromDomain(RequestForm requestForm){
        return new RequestFormData(requestForm.getMonto(),
                requestForm.getPlazo(), requestForm.getEmail() , requestForm.getId_tipo_prestamo());
    }

    public RequestForm toDomain() {
        return new RequestForm(this.monto, this.plazo, this.email, this.id_tipo_prestamo);
    }
}
