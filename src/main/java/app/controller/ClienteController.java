package app.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.PedidoDTO;
import app.DTO.PedidoResponseDTO;
import app.entity.Pedido;
import app.entity.Usuario;
import app.service.PedidoService;
import app.util.StatusPedido;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class ClienteController {	
	
	@Autowired
	PedidoService pedidoService;
	

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/me/pedidos")
	public Page<PedidoResponseDTO> getMeusPedidos(
	        @AuthenticationPrincipal Usuario cliente,
	        @RequestParam(required = false) StatusPedido status,
	        Pageable pageable) {

	    Page<Pedido>  pages = pedidoService.getMeusPedidos(cliente, status, pageable);
	    
	    return pages.map(PedidoResponseDTO::from);
	    
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/me/pedidos")
	public Pedido createMeuPedido(@AuthenticationPrincipal Usuario cliente,
								@RequestBody @Valid PedidoDTO data) {
	
		return this.pedidoService.createMeuPedido(cliente, data);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/me/pedidos/{id}/codigo")
	public ResponseEntity<String> getCodigo(@AuthenticationPrincipal Usuario cliente,
											@PathVariable long id) {
		return this.pedidoService.findCodigoEntregaByClienteAndPedidoId(cliente, id);
	}
	
	//Avaliação @PutMapping
}