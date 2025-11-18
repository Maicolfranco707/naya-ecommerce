package com.naya.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;
    
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false)
    private String direccion;
    
    private String barrio;
    
    @Column(nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}
