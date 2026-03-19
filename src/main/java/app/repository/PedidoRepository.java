package app.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Pedido;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.util.StatusPedido;
import java.util.Optional;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {

   Page<Pedido> findByClienteAndStatusOrderByDataHoraPedido(Usuario cliente, StatusPedido status, Pageable pageable);
   
   Page<Pedido> findByClienteOrderByDataHoraPedido(Usuario cliente, Pageable pageable);

   Page<Pedido> findByQuiosqueOrderByDataHoraPedido(Quiosque quiosque, Pageable pageable);
   
   Page<Pedido> findByQuiosqueAndStatusOrderByDataHoraPedido(Quiosque quiosque, StatusPedido status, Pageable pageable);
   
   Optional<Pedido> findByClienteAndId(Usuario cliente, long id);
   
   Optional<Pedido> findByIdAndEntregador(Long id, Usuario entregador);
}
