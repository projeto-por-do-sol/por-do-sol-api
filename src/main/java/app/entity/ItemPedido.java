package app.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Integer quantidade;
	private BigDecimal valorUnit, subTotal;
	
	@ManyToOne
	@JoinColumn(name="idItem")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="idPedido")
	private Pedido pedido;
}
