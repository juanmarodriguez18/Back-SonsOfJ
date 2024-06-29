package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends BaseRepository<Factura,Long>{

    @Query("SELECT f FROM Factura f WHERE f.pedido.id = :id")
    public Factura getByPedidoId(long id);

}
