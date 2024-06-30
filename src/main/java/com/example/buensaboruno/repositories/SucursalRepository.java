package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Articulo;
import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import com.example.buensaboruno.domain.entities.ArticuloManufacturado;
import com.example.buensaboruno.domain.entities.Sucursal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SucursalRepository extends BaseRepository<Sucursal,Long> {
    @Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.promociones WHERE s.id = :id")
    Sucursal findWithPromocionesById(@Param("id") Long id);

    @Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.empleados WHERE s.id = :id")
    Sucursal findWithEmpleadosById(@Param("id") Long id);

    @Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.categorias WHERE s.id = :id")
    Sucursal findWithCategoriasById(@Param("id") Long id);

    @Query("SELECT am FROM ArticuloManufacturado am WHERE am.sucursal.id = :id")
    List<ArticuloManufacturado> findArticulosManufacturadosBySucursalId(@Param("id") Long id);

    @Query("SELECT ai FROM ArticuloInsumo ai WHERE ai.sucursal.id = :id")
    List<ArticuloInsumo> findArticulosInsumosBySucursalId(@Param("id") Long id);

    /*@Query("SELECT am FROM ArticuloManufacturado am JOIN am a WHERE a.sucursal.id = :sucursalId")
    List<ArticuloManufacturado> findArticulosManufacturadosBySucursalId(@Param("sucursalId") Long sucursalId);

    @Query("SELECT ai FROM ArticuloInsumo ai JOIN ai a WHERE a.sucursal.id = :sucursalId")
    List<ArticuloInsumo> findArticulosInsumosBySucursalId(@Param("sucursalId") Long sucursalId);*/
}
