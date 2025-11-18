package com.naya.ecommerce.repository;

import com.naya.ecommerce.model.Cliente;
import com.naya.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteOrderByFechaPedidoDesc(Cliente cliente);
    List<Pedido> findByEstadoOrderByFechaPedidoDesc(Pedido.EstadoPedido estado);
    List<Pedido> findByFechaPedidoBetweenOrderByFechaPedidoDesc(LocalDateTime inicio, LocalDateTime fin);
    List<Pedido> findAllByOrderByFechaPedidoDesc();
}
