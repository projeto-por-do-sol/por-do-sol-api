package app.entity;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import app.util.StatusPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.core.io.BigDecimalParser;

@SuppressWarnings("unused")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	private BigDecimal valorTotal;
	private LocalDateTime dataHoraPedido;
	private double latitudeEntrega, longitudeEntrega;
	private String codigoEntrega;
	
	@Embedded
	private Avaliacao avaliacao;
	
	@ManyToOne
	@JoinColumn(name = "idFormaPagamento")
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name = "idQuiosque")
	private Quiosque quiosque;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itemPedido;
	
	@ManyToOne
	@JoinColumn(name = "idEntregador")
	private Usuario entregador;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;	
	
	public String gerarCodigo() { //
		SecureRandom random = new SecureRandom();
		int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
	}
	
	@PrePersist
	public void gerarCodigoEntrega() {
	    if (this.codigoEntrega == null) 
	        this.codigoEntrega = gerarCodigo();    
	}
	
	public void calcularValorTot() {
		if (itemPedido == null || itemPedido.isEmpty()) {
	        this.valorTotal = BigDecimal.ZERO;
	        return;
	    }
		this.setValorTotal(itemPedido.stream()
		        .map(ItemPedido::getSubTotal)
		        .reduce(BigDecimal.ZERO, BigDecimal::add));
	}

	public Pedido(LocalDateTime dataHoraPedido, double latitudeEntrega, double longitudeEntrega,
			Usuario cliente, Quiosque quiosque, List<ItemPedido> itemPedido,StatusPedido status) {
		super();
		this.dataHoraPedido = dataHoraPedido;
		this.latitudeEntrega = latitudeEntrega;
		this.longitudeEntrega = longitudeEntrega;
		this.cliente = cliente;
		this.quiosque = quiosque;
		this.itemPedido = itemPedido;
		this.status = status;
	}
	
	public void addItem(ItemPedido item) {
	    item.setPedido(this);
	    this.itemPedido.add(item);
	}
	
	public void setFormaPagamento(FormaPagamento formaPagamento) {
	    this.formaPagamento = formaPagamento;
	    formaPagamento.getPedidos().add(this);
	}
}
