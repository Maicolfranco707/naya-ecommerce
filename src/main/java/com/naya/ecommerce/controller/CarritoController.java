package com.naya.ecommerce.controller;

import com.naya.ecommerce.model.CarritoItem;
import com.naya.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;
    
    @GetMapping
    public String verCarrito(Model model) {
        List<CarritoItem> items = carritoService.getItems();
        model.addAttribute("items", items);
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems());
        return "carrito";
    }
    
    @PostMapping("/actualizar/{productoId}")
    public String actualizarCantidad(@PathVariable Long productoId, 
                                     @RequestParam Integer cantidad) {
        carritoService.actualizarCantidad(productoId, cantidad);
        return "redirect:/carrito";
    }
    
    @PostMapping("/eliminar/{productoId}")
    public String eliminarProducto(@PathVariable Long productoId) {
        carritoService.eliminarProducto(productoId);
        return "redirect:/carrito";
    }
    
    @GetMapping("/limpiar")
    public String limpiarCarrito() {
        carritoService.limpiar();
        return "redirect:/carrito";
    }
}
