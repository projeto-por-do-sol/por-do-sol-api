package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.ItemDTO;
import app.entity.Item;
import app.entity.Usuario;
import app.service.ItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/items")
//@CrossOrigin("http://localhost")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'PROPRIETARIO')")
	public ResponseEntity<ItemDTO> save(
			@AuthenticationPrincipal Usuario administrador,
			@RequestBody @Valid ItemDTO data){
		ItemDTO item = this.itemService.save(administrador, data);
	    return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> findById(@PathVariable Long id){
			Item item = this.itemService.findById(id);
			return ResponseEntity.ok(item);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PROPRIETARIO')")
	public ResponseEntity<Item> update(@AuthenticationPrincipal Usuario administrador, @PathVariable Long id, @RequestBody @Valid ItemDTO data){		
			Item item = this.itemService.update(administrador, id, data);
			return ResponseEntity.ok(item);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('PROPRIETARIO')")
	public ResponseEntity<String> delete(@AuthenticationPrincipal Usuario administrador, @PathVariable Long id){
			this.itemService.deleteById(administrador, id);
			return ResponseEntity.noContent().build();
	}
}
