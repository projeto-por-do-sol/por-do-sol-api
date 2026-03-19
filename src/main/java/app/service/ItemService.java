package app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.DTO.ItemDTO;
import app.entity.Empresa;
import app.entity.Item;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.repository.EmpresaRepository;
import app.repository.ItemRepository;
import app.repository.QuiosqueRepository;
import app.util.UserRole;
import jakarta.transaction.Transactional;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private QuiosqueRepository quiosqueRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional
	public ItemDTO save(Usuario administrador, ItemDTO data) {
	    Quiosque quiosque = findQuiosqueForUser(administrador, data);

	    Item item = new Item(
	        data.nome(),
	        data.tipo(),
	        data.descricao(),
	        data.valor(),
	        quiosque
	    );
	    itemRepository.save(item);
	    return data;
	}
	private Quiosque findQuiosqueForUser(Usuario usuario, ItemDTO data) {
	    if (usuario.getRole().equals(UserRole.ADMIN)) {
	        return quiosqueRepository.findByAdministradores(usuario)
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Quiosque não encontrado"
	            ));
	    } else {

	        Empresa empresa = empresaRepository.findByProprietario(usuario)
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Empresa não encontrada"
	            ));

	        Quiosque quiosque = quiosqueRepository.findById(data.id())
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Quiosque não encontrado"
	            ));

	        if (!empresa.getQuiosques().contains(quiosque)) {
	            throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "Quiosque não pertence à empresa do usuário"
	            );
	        }

	        return quiosque;
	    }
	}
	
	@Transactional
	public Item findById(Long id){
		Item item = itemRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Item não encontrado")));
		return item;	
	}
	
	@Transactional
	public Item update(Usuario administrador, Long id, ItemDTO data) {
		Quiosque quiosque = this.quiosqueRepository.findByAdministradores(administrador)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Quiosque não encontrado")));
		
	    Item item = itemRepository.findByIdAndQuiosque(id, quiosque)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Item não encontrado nesse quiosque")));

	    item.setNome(data.nome());
	    item.setTipo(data.tipo());
	    item.setDescricao(data.descricao());
	    item.setValor(data.valor());	    
	    return itemRepository.save(item);
	}
	
	@Transactional
	public void deleteById(Usuario administrador, Long id){
		Quiosque quiosque = this.quiosqueRepository.findByAdministradores(administrador)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Quiosque não encontrado")));
		
		Item item = itemRepository.findByIdAndQuiosque(id, quiosque)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Item não encontrado nesse quiosque")));

		this.itemRepository.delete(item);	
	}

}
