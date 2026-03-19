package app.DTO;

import jakarta.validation.constraints.NotNull;

public record FormaPagamentoDTO(
		@NotNull
	    String tipo,
	    String chavePix,
	    String numeroCartao,
	    String bandeira,
	    String codigoSeguranca
	) {
	
	public boolean isValido() {
		if ("CARTAO".equals(tipo)) {
	        return numeroCartao != null && bandeira != null && codigoSeguranca != null;
	    }
	    if ("PIX".equals(tipo)) {
	        return chavePix != null;
	    }
	    return false;
	}
}