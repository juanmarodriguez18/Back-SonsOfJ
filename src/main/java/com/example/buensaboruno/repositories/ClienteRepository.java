package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Base;
import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente,Long> {
    @Query(value = "SELECT * FROM CLIENTE WHERE email = :email", nativeQuery = true)
    Optional<Cliente> findByEmail(String email);
}
