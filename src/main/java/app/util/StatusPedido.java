package app.util;

import lombok.Getter;

@Getter
public enum StatusPedido {
    CRIADO,
    PAGO,
    PREPARANDO,
    EM_ENTREGA,
    FINALIZADO,
    CANCELADO
}
