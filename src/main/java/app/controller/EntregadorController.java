package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.ValidarCodigoDTO;
import app.entity.Pedido;
import app.entity.Usuario;
import app.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entregador")

public class EntregadorController {
	
	@Autowired
	PedidoService pedidoService;
	
	@PreAuthorize("hasRole('Entregador')")
	@PostMapping("/validarcodigo")
	public boolean validarCodigo(@AuthenticationPrincipal Usuario entregador,
						 @RequestBody @Valid ValidarCodigoDTO data) {
		return pedidoService.validarCodigo(entregador, data);
	}
	
//	@PreAuthorize("hasRole('Entregador')")
//	@PostMapping("/Entrega")
//	public Pedido Entregar(@AuthenticationPrincipal Usuario entregador) {
//		return pedidoService.Entregar(entregador);
//	}
}	
	
	