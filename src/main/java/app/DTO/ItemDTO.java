package app.DTO;

import java.math.BigDecimal;

public record ItemDTO(String nome, String tipo, String descricao, BigDecimal valor, long id) {

}
