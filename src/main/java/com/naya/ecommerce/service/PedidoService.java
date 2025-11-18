package com.naya.ecommerce.service;

import com.naya.ecommerce.model.*;
import com.naya.ecommerce.repository.ClienteRepository;
import com.naya.ecommerce.repository.PedidoRepository;
import com.naya.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ProductoService productoService;
    
    public Pedido crearPedido(Cliente cliente, List<CarritoItem> items, 
                              Pedido.MetodoPago metodoPago, String observaciones) {
        
        // Guardar o buscar cliente
        Cliente clienteGuardado = clienteRepository.findByTelefono(cliente.getTelefono())
                .orElseGet(() -> clienteRepository.save(cliente));
        
        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteGuardado);
        pedido.setMetodoPago(metodoPago);
        pedido.setObservaciones(observaciones);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        
        // Crear detalles del pedido
        for (CarritoItem item : items) {
            Optional<Producto> productoOpt = productoRepository.findById(item.getProductoId());
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                
                DetallePedido detalle = new DetallePedido();
                detalle.setProducto(producto);
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.calcularSubtotal();
                
                pedido.agregarDetalle(detalle);
                
                // Reducir stock
                productoService.reducirStock(producto.getId(), item.getCantidad());
            }
        }
        
        // Calcular total
        pedido.calcularTotal();
        
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaPedidoDesc();
    }
    
    public List<Pedido> listarPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstadoOrderByFechaPedidoDesc(estado);
    }
    
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    public Pedido actualizarEstado(Long id, Pedido.EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> listarPorCliente(Cliente cliente) {
        return pedidoRepository.findByClienteOrderByFechaPedidoDesc(cliente);
    }
}
