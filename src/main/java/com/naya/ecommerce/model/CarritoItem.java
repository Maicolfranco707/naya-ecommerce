package com.naya.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {
    private Long productoId;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String imagen;
    
    public Double getSubtotal() {
        return precio * cantidad;
    }
}
