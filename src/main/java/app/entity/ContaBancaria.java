package app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class ContaBancaria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "idQuiosque")
	private Quiosque quiosque;
	
	private String numero, agencia;
	private BigDecimal saldo;
}
