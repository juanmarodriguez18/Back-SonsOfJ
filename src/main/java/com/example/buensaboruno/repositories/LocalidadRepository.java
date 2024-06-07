package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Localidad;
import com.example.buensaboruno.domain.entities.Provincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad,Long>{
    @Query("SELECT l FROM Localidad l WHERE l.provincia.nombre = :provincia")
    List<Localidad> findByProvincia(@Param("provincia") String provincia);
    Localidad findByNombre(String nombre);
}
