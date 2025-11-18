package com.naya.ecommerce.controller;

import com.naya.ecommerce.model.Producto;
import com.naya.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductoService productoService;
    
    @Override
    public void run(String... args) throws Exception {
        if (productoService.listarTodos().isEmpty()) {
            System.out.println("Inicializando datos de productos...");
            
            crearProducto("Shampoo Extracto de Cayena", 
                "Favorece el crecimiento del cabello previniendo la caída del folículo piloso", 
                34000.0, "CAPILAR", "550ml", 
                "/imagenes/shampoo-cayena.jpg");
            
            crearProducto("Acondicionador Extracto de Cayena", 
                "Aporta suavidad, brillo y da vida a la hebra capilar", 
                34000.0, "CAPILAR", "550ml",
                "/imagenes/acondicionador-cayena.jpg");
            
            crearProducto("Shampoo Extracto de Romero", 
                "Contribuye a disminuir la fragilidad del cabello y fortalece la fibra capilar", 
                34000.0, "CAPILAR", "550ml",
                "/imagenes/shampoo-romero.jpg");
            
            crearProducto("Acondicionador Extracto de Romero", 
                "Acondiciona y da sedosidad al cabello", 
                34000.0, "CAPILAR", "550ml",
                "/imagenes/acondicionador-romero.jpg");
            
            crearProducto("Ampolleta Nutrición", 
                "Recupera y transforma tu cabello con nutrición intensiva", 
                18000.0, "CAPILAR", "30ml",
                "/imagenes/ampolleta-nutricion.jpg");
            
            crearProducto("Ampolleta Crecimiento", 
                "Estimula el crecimiento capilar de manera natural y efectiva", 
                18000.0, "CAPILAR", "30ml",
                "/imagenes/ampolleta-crecimiento.jpg");
            
            crearProducto("Tónico Capilar Intensivo", 
                "Tratamiento premium de crecimiento a base de aceites naturales", 
                25000.0, "CAPILAR", "Presentación especial",
                "/imagenes/tonico-capilar.jpg");
            
            crearProducto("Mantequilla Coco Festival", 
                "Hidratación profunda con vitamina E", 
                28500.0, "CORPORAL", "350g",
                "/imagenes/mantequilla-coco.jpg");
            
            crearProducto("Mantequilla Cherry", 
                "Dulce y nutritiva, deja tu piel suave e hidratada", 
                28500.0, "CORPORAL", "Presentación estándar",
                "/imagenes/mantequilla-cherry.jpg");
            
            crearProducto("Mantequilla Uva", 
                "Enriquecida con antioxidantes naturales", 
                28500.0, "CORPORAL", "Presentación estándar",
                "/imagenes/mantequilla-uva.jpg");
            
            crearProducto("Mantequilla Kiwi", 
                "Vitaminas y frescura natural", 
                28500.0, "CORPORAL", "Presentación estándar",
                "/imagenes/mantequilla-kiwi.jpg");
            
            System.out.println("✅ Datos de productos inicializados correctamente!");
        }
    }
    
    private void crearProducto(String nombre, String descripcion, Double precio, 
                               String categoria, String tamanio, String imagen) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setTamanio(tamanio);
        producto.setImagen(imagen);
        producto.setStock(100);
        producto.setActivo(true);
        productoService.guardar(producto);
    }
}