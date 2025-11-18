package com.naya.ecommerce.controller;

import com.naya.ecommerce.model.Cliente;
import com.naya.ecommerce.model.Pedido;
import com.naya.ecommerce.service.CarritoService;
import com.naya.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    
    @Autowired
    private CarritoService carritoService;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Value("${naya.whatsapp.number}")
    private String whatsappNumber;
    
    @GetMapping
    public String checkout(Model model) {
        if (carritoService.estaVacio()) {
            return "redirect:/carrito";
        }
        
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("metodosPago", Pedido.MetodoPago.values());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems());
        return "checkout";
    }
    
    @PostMapping("/procesar")
    public String procesarPedido(@Valid @ModelAttribute Cliente cliente,
                                 BindingResult result,
                                 @RequestParam Pedido.MetodoPago metodoPago,
                                 @RequestParam(required = false) String observaciones,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("items", carritoService.getItems());
            model.addAttribute("total", carritoService.getTotal());
            model.addAttribute("metodosPago", Pedido.MetodoPago.values());
            return "checkout";
        }
        
        try {
            // Crear pedido
            Pedido pedido = pedidoService.crearPedido(
                cliente,
                carritoService.getItems(),
                metodoPago,
                observaciones
            );
            
            // Limpiar carrito
            carritoService.limpiar();
            
            // Redirigir a confirmación
            redirectAttributes.addAttribute("pedidoId", pedido.getId());
            return "redirect:/checkout/confirmacion";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar el pedido: " + e.getMessage());
            model.addAttribute("items", carritoService.getItems());
            model.addAttribute("total", carritoService.getTotal());
            model.addAttribute("metodosPago", Pedido.MetodoPago.values());
            return "checkout";
        }
    }
    
    @GetMapping("/confirmacion")
    public String confirmacion(@RequestParam Long pedidoId, Model model) {
        return pedidoService.buscarPorId(pedidoId)
                .map(pedido -> {
                    model.addAttribute("pedido", pedido);
                    
                    // Generar mensaje de WhatsApp
                    String mensaje = generarMensajeWhatsApp(pedido);
                    try {
                        String mensajeCodificado = URLEncoder.encode(mensaje, "UTF-8");
                        String urlWhatsApp = "https://wa.me/" + whatsappNumber + "?text=" + mensajeCodificado;
                        model.addAttribute("urlWhatsApp", urlWhatsApp);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    return "confirmacion";
                })
                .orElse("redirect:/");
    }
    
    private String generarMensajeWhatsApp(Pedido pedido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("🌿 *NUEVO PEDIDO NAYA* 🌿\n\n");
        mensaje.append("📋 *Pedido #").append(pedido.getId()).append("*\n\n");
        mensaje.append("👤 *Cliente:*\n");
        mensaje.append("Nombre: ").append(pedido.getCliente().getNombre()).append("\n");
        mensaje.append("Teléfono: ").append(pedido.getCliente().getTelefono()).append("\n");
        mensaje.append("Dirección: ").append(pedido.getCliente().getDireccion()).append("\n");
        if (pedido.getCliente().getBarrio() != null) {
            mensaje.append("Barrio: ").append(pedido.getCliente().getBarrio()).append("\n");
        }
        mensaje.append("\n🛍️ *Productos:*\n");
        
        for (var detalle : pedido.getDetalles()) {
            mensaje.append("• ").append(detalle.getProducto().getNombre())
                   .append(" x").append(detalle.getCantidad())
                   .append(" = $").append(String.format("%.0f", detalle.getSubtotal()))
                   .append("\n");
        }
        
        mensaje.append("\n💰 *Total: $").append(String.format("%.0f", pedido.getTotal())).append("*\n");
        mensaje.append("💳 *Método de pago:* ").append(pedido.getMetodoPago()).append("\n");
        
        if (pedido.getObservaciones() != null && !pedido.getObservaciones().isEmpty()) {
            mensaje.append("\n📝 *Observaciones:*\n").append(pedido.getObservaciones());
        }
        
        return mensaje.toString();
    }
}
