package co.com.pragmarestreq.model.state;
import lombok.*;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class State {
    public Integer idEstado;
    public String nombre;
    public String descripcion;
}
