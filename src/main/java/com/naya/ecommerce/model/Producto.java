package com.naya.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;
    
    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false)
    private String categoria; // CAPILAR, CORPORAL
    
    private String imagen; // URL o ruta de la imagen
    
    @Column(nullable = false)
    private Integer stock = 100; // Stock disponible
    
    private String tamanio; // 550ml, 350g, 30ml, etc.
    
    @Column(nullable = false)
    private Boolean activo = true; // Para activar/desactivar productos
}
