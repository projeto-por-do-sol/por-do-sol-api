package app.controller;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.QuiosqueCreateDTO;
import app.DTO.QuiosqueDTO;
import app.entity.Item;
import app.entity.Pedido;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.service.PedidoService;
import app.service.QuiosqueService;
import app.util.StatusPedido;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/quiosque")
//@CrossOrigin("http://localhost")
public class QuiosqueController {
	
	@Autowired
	private QuiosqueService quiosqueService;	
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/nearby")
	public ResponseEntity<List<Quiosque>> findByDistancia(
			@RequestParam double latUsuario,
			@RequestParam double lonUsuario,
			@RequestParam double raioM) {
		try {
			List<Quiosque> lista = this.quiosqueService.findByDistancia(latUsuario, lonUsuario, raioM);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
//			return ResponseEntity.notFound().build();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Quiosque> findById(@PathVariable long id) {
		return new ResponseEntity<>(this.quiosqueService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/items")
	public ResponseEntity<List<Item>> findByQuiosque(@PathVariable long id) {
		return new ResponseEntity<>(this.quiosqueService.findByQuiosque(id), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('PROPRIETARIO')")
	public ResponseEntity<QuiosqueCreateDTO> save(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid QuiosqueDTO data) {
		try {			
			QuiosqueCreateDTO quiosque = this.quiosqueService.save(usuario, data);
			 return ResponseEntity.status(HttpStatus.CREATED).body(quiosque);		
		} catch (Exception e) {			
			return ResponseEntity.badRequest().build();			
		}
	}
	
	@GetMapping("/{quiosqueId}/pedidos")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Pedido> getPedidos(
	        @AuthenticationPrincipal Usuario admin,
	        @PathVariable long quiosqueId,
	        Pageable pageable) {
		
	    return pedidoService.getPedidos(admin, quiosqueId, pageable);
	}
	
	@PutMapping("/{quiosqueId}/pedidos/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Pedido updatePedidos(
			@PathVariable long id,
	        @AuthenticationPrincipal Usuario admin,
	        @PathVariable long quiosqueId,
	        @RequestParam StatusPedido status) {
	    return pedidoService.updatePedido(id, quiosqueId, admin, status);
	}
}
