package co.com.pragmarestreq.model.requestform;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateRequestFormState {
    private Integer idAlias;
    private Integer idEstado;
}
