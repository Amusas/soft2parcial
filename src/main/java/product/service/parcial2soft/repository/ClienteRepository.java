package product.service.parcial2soft.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.service.parcial2soft.entity.Cliente;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Buscar clientes activos
    java.util.List<Cliente> findByActivoTrue();

    boolean existsByEmail(String email);

    Optional<Cliente> findByEmail(String email);
}