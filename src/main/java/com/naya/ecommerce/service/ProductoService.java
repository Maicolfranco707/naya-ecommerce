package com.naya.ecommerce.service;

import com.naya.ecommerce.model.Producto;
import com.naya.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> listarTodos() {
        return productoRepository.findByActivoTrue();
    }
    
    public List<Producto> listarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaAndActivoTrue(categoria);
    }
    
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre);
    }
    
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
    
    public void desactivar(Long id) {
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setActivo(false);
            productoRepository.save(producto);
        });
    }
    
    public boolean hayStock(Long id, Integer cantidad) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.isPresent() && producto.get().getStock() >= cantidad;
    }
    
    public void reducirStock(Long id, Integer cantidad) {
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);
        });
    }
}
