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
@Table(name = "estado")
public class StateData {
    @Id
    @Column("id_estado")
    private Integer idEstado;
    private String nombre;
    private String descripcion;

}
