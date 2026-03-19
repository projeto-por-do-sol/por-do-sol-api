package app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.util.StatusConta;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carteira {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private BigDecimal saldo;
	
	@OneToOne(cascade = CascadeType.ALL)	
	private Usuario cliente;
	
	@Enumerated(EnumType.STRING)
	private StatusConta status;	
	private LocalDate dataCriacao;
	
	public boolean Transacao() {
		// Implement
		return true;
	}
}
