package com.naya.ecommerce.repository;

import com.naya.ecommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByTelefono(String telefono);
    Optional<Cliente> findByEmail(String email);
}
