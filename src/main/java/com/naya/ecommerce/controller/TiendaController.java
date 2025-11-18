package com.naya.ecommerce.controller;

import com.naya.ecommerce.model.Producto;
import com.naya.ecommerce.service.CarritoService;
import com.naya.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TiendaController {
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CarritoService carritoService;
    
    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
    
    @GetMapping("/tienda")
    public String tienda(Model model) {
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems());
        return "index";
    }
    
    @GetMapping("/productos")
    public String productos(@RequestParam(required = false) String categoria, Model model) {
        List<Producto> productos;
        if (categoria != null && !categoria.isEmpty()) {
            productos = productoService.listarPorCategoria(categoria);
        } else {
            productos = productoService.listarTodos();
        }
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaActual", categoria);
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems());
        return "productos";
    }
    
    @GetMapping("/producto/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        return productoService.buscarPorId(id)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    model.addAttribute("cantidadCarrito", carritoService.getCantidadItems());
                    return "detalle-producto";
                })
                .orElse("redirect:/tienda");
    }
    
    @PostMapping("/carrito/agregar/{id}")
public String agregarAlCarrito(@PathVariable Long id) {
    productoService.buscarPorId(id).ifPresent(producto -> {
        carritoService.agregarProducto(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            producto.getImagen()
        );
    });
    return "redirect:/carrito";
}
}