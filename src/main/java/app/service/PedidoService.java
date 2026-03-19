package app.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.DTO.FormaPagamentoDTO;
import app.DTO.ItemPedidoDTO;
import app.DTO.PedidoDTO;
import app.DTO.ValidarCodigoDTO;
import app.entity.Cartao;
import app.entity.Empresa;
import app.entity.FormaPagamento;
import app.entity.Item;
import app.entity.ItemPedido;
import app.entity.Pedido;
import app.entity.Pix;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.repository.EmpresaRepository;
import app.repository.FormaPagamentoRepository;
import app.repository.ItemRepository;
import app.repository.PedidoRepository;
import app.repository.QuiosqueRepository;
import app.util.StatusPedido;
import app.util.UserRole;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	QuiosqueRepository quiosqueRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	@Transactional
	public Pedido createMeuPedido(
			Usuario cliente, 
			PedidoDTO data) {
		
		
		if (!data.formaPagamento().isValido()) 
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados de pagamento inválidos");		
		FormaPagamento formaPagamento = criarFormaPagamento(data.formaPagamento());
		formaPagamento = formaPagamentoRepository.save(formaPagamento);
		
		
		Quiosque quiosque = quiosqueRepository.findById(data.quiosque())
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiosque não encontrado"));

		Pedido pedido = new Pedido(
		        data.dataHoraPedido(),
		        data.latitudeEntrega(),
		        data.longitudeEntrega(),
		        cliente,
		        quiosque,
		        new ArrayList<>(),
		        StatusPedido.CRIADO
		);
				
		pedido.setFormaPagamento(formaPagamento);
	
		List<Long> itemIds = data.itens().stream().map(ItemPedidoDTO::itemId).toList();
		if (itemIds.size() != new HashSet<>(itemIds).size()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem IDs duplicados");
		
		List<Item> itensBanco = itemRepository.findAllById(itemIds);
		if (itensBanco.size() != itemIds.size()) 
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Um ou mais itens não foram encontrados");
		
		Map<Long, Item> itensMap = itensBanco.stream()
		        .collect(Collectors.toMap(Item::getId, Function.identity()));
						
		for (ItemPedidoDTO itemDTO : data.itens()) {

		    Item item = itensMap.get(itemDTO.itemId());

		    if (item == null) 
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado");
		    

		    if (item.getQuiosque() == null || !item.getQuiosque().getId().equals(quiosque.getId()))
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item não pertence ao quiosque");
		    

		    ItemPedido itemPedido = new ItemPedido();
		    itemPedido.setItem(item);
		    itemPedido.setQuantidade(itemDTO.quantidade());
		    itemPedido.setValorUnit(item.getValor());

		    itemPedido.setSubTotal(
		        item.getValor().multiply(BigDecimal.valueOf(itemDTO.quantidade()))
		    );

		    pedido.addItem(itemPedido);
		}
		pedido.calcularValorTot();		
		formaPagamento.setValor(pedido.getValorTotal());
		//formaPagamento.processarPagamento();
		
		formaPagamentoRepository.save(formaPagamento);
		
		
		
		return pedidoRepository.save(pedido);
	}
	
	private FormaPagamento criarFormaPagamento(FormaPagamentoDTO dto) {

	    switch (dto.tipo()) {

	        case "PIX" -> {
	            Pix pix = new Pix();
	            pix.setChavePix(dto.chavePix());
	            return pix;
	        }

	        case "CARTAO" -> {
	            Cartao cartao = new Cartao();
	            cartao.setNumeroCartao(dto.numeroCartao());
	            cartao.setBandeira(dto.bandeira());
	            cartao.setCodigoSeguranca(dto.codigoSeguranca());
	            return cartao;
	        }

	        default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de pagamento inválido");
	    }
	}	
	
	public Page<Pedido> getMeusPedidos(
			Usuario cliente, 
			StatusPedido status, 
			Pageable pageable) {
		
		
		return status == null
			? this.pedidoRepository.findByClienteOrderByDataHoraPedido(cliente, pageable)
		    : this.pedidoRepository.findByClienteAndStatusOrderByDataHoraPedido(cliente, status, pageable);
	}
	
	
	
	public Page<Pedido> getPedidos(
			Usuario administrador, 
			long quiosqueId,
			Pageable pageable) {
		
		Quiosque quiosque = findQuiosqueForUser(administrador, quiosqueId);				
		return pedidoRepository.findByQuiosqueOrderByDataHoraPedido(quiosque, pageable);

	}
	
	public Pedido updatePedido(
			long id, 
			long quiosqueId, Usuario 
			administrador, 
			StatusPedido status) {	
		
		
		Pedido pedido = pedidoRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Pedido não encontrado")));
		pedido.setStatus(status);
		Quiosque quiosque = findQuiosqueForUser(administrador, quiosqueId);
		
		
		if(pedido.getQuiosque() == quiosque)
			return this.pedidoRepository.save(pedido);
		return null;				
	}
	
	public ResponseEntity<String> findCodigoEntregaByClienteAndPedidoId(
			Usuario cliente,
			long id) {
		
		
		Pedido pedido = pedidoRepository.findByClienteAndId(cliente, id)
				 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Pedido não encontrado")));
		StatusPedido status = pedido.getStatus();
		if (StatusPedido.CANCELADO.equals(status) || StatusPedido.FINALIZADO.equals(status))
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Pedido não está disponível para obter código");
		return ResponseEntity.ok(pedido.getCodigoEntrega());
	}
	
	public boolean validarCodigo(Usuario entregador, ValidarCodigoDTO data) {
	    Pedido pedido = pedidoRepository
	        .findByIdAndEntregador(data.pedido(), entregador)
	        .orElseThrow(() -> new ResponseStatusException(
	            HttpStatus.NOT_FOUND, "Pedido não encontrado"));

	    return pedido.getCodigoEntrega().equals(data.codigo());
	}
	
	
	private Quiosque findQuiosqueForUser(
			Usuario admin,
			long id) {
		
		
	    if (admin.getRole().equals(UserRole.ADMIN)) {
	        return quiosqueRepository.findByAdministradoresContaining(admin)
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Quiosque não encontrado"
	            ));
	    } else {

	        Empresa empresa = empresaRepository.findByProprietario(admin)
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Empresa não encontrada"
	            ));

	        Quiosque quiosque = quiosqueRepository.findById(id)
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
}
