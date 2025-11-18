package com.naya.ecommerce.repository;

import com.naya.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaAndActivoTrue(String categoria);
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
}
