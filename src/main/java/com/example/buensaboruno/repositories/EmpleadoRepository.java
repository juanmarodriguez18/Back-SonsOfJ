package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends BaseRepository<Empleado,Long> {
    @Query(value = "SELECT * FROM EMPLEADO WHERE email = :email", nativeQuery = true)
    Optional<Empleado> findByEmail(String email);
}
