package app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("unused")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("CARTAO")
public class Cartao  extends FormaPagamento {
	
	private String numeroCartao, bandeira, codigoSeguranca;
	
	@Override
    public boolean processarPagamento() {
        return true;
    }
}
