package app.DTO;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(
		@NotNull
	    Long quiosque,
	    @NotNull
	    LocalDateTime dataHoraPedido,
	    @NotNull
	    FormaPagamentoDTO formaPagamento,
	    @NotNull
	    List<@Valid ItemPedidoDTO> itens,
	    @NotNull
	    double latitudeEntrega,
	    @NotNull
	    double longitudeEntrega
	) {}
