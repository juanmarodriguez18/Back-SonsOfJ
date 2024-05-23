package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.ArticuloManufacturadoDetalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloManufacturadoDetalleRepository extends BaseRepository<ArticuloManufacturadoDetalle,Long> {
    // Consulta para obtener el último id ordenando de forma descendente y tomando el primero
    @Query(value = "SELECT MAX(id) FROM ArticuloManufacturadoDetalle")
    Long obtenerUltimoId();
}
