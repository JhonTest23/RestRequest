package co.com.pragmarestreq.model.user;
import lombok.*;
//import lombok.NoArgsConstructor;


@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String documentoIdentidad;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String direccion;
    private Integer telefono;
    private String email;
    private Double salarioBase;
    private Integer idRol;
    private String contrasena;
    private Boolean activo;
}