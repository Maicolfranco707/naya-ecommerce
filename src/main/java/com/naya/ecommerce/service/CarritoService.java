package com.naya.ecommerce.service;

import com.naya.ecommerce.model.CarritoItem;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CarritoService {
    
    private List<CarritoItem> items = new ArrayList<>();
    
    public void agregarProducto(Long productoId, String nombre, Double precio, String imagen) {
        Optional<CarritoItem> itemExistente = items.stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst();
        
        if (itemExistente.isPresent()) {
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + 1);
        } else {
            CarritoItem nuevoItem = new CarritoItem(productoId, nombre, precio, 1, imagen);
            items.add(nuevoItem);
        }
    }
    
    public void actualizarCantidad(Long productoId, Integer cantidad) {
        items.stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .findFirst()
                .ifPresent(item -> {
                    if (cantidad <= 0) {
                        items.remove(item);
                    } else {
                        item.setCantidad(cantidad);
                    }
                });
    }
    
    public void eliminarProducto(Long productoId) {
        items.removeIf(item -> item.getProductoId().equals(productoId));
    }
    
    public List<CarritoItem> getItems() {
        return items;
    }
    
    public Double getTotal() {
        return items.stream()
                .mapToDouble(CarritoItem::getSubtotal)
                .sum();
    }
    
    public Integer getCantidadItems() {
        return items.stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum();
    }
    
    public void limpiar() {
        items.clear();
    }
    
    public boolean estaVacio() {
        return items.isEmpty();
    }
}
