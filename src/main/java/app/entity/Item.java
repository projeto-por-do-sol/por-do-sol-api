package app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
public class Item {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public Item(String nome, String tipo, String descricao, BigDecimal valor, Quiosque quiosque) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.descricao = descricao;
		this.valor = valor;
		this.quiosque = quiosque;
	}
	private String nome, tipo, descricao;
	private BigDecimal valor;	
	@ManyToOne
	@JoinColumn(name="idQuiosque", nullable = false)
	private Quiosque quiosque;	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private List<ItemPedido> itemPedido;	
}
