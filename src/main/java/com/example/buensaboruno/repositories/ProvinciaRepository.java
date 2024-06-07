package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Provincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia,Long>{
    @Query("SELECT p FROM Provincia p WHERE p.pais.nombre = :pais")
    List<Provincia> findByPais(@Param("pais") String pais);
}
