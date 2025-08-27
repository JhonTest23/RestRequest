package co.com.pragmarestreq.r2dbc.entity;

import co.com.pragmarestreq.model.state.State;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceUnit;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estado")
public class StateData {
    @Id
    public long id_estado;
    public String nombre;
    public String descripcion;

    public StateData(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public State toDomain(){
        return  new State(this.nombre, this.descripcion);
    }
}
