package app.entity;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("unused")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Avaliacao {
    private Integer nota;
    private String descricaoAvaliacao;
}
