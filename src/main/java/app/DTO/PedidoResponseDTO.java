package app.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import app.entity.Avaliacao;
import app.entity.Pedido;
import app.util.StatusPedido;

public record PedidoResponseDTO(
		BigDecimal valorTotal,
		LocalDateTime dataHoraPedido,
		Avaliacao avaliacao,
//		long formaPagamento,
		String quiosque,
		String entregador,
		StatusPedido status
		) {
	public static PedidoResponseDTO from(Pedido p) {
	    return new PedidoResponseDTO(
	        p.getValorTotal(),
	        p.getDataHoraPedido(),
	        p.getAvaliacao(),
//	        p.getFormaPagamento().getId(),
	        p.getQuiosque().getNome(),
	        p.getEntregador() != null ? p.getEntregador().getNome() : null,
	        p.getStatus()
	    );
	}
}
