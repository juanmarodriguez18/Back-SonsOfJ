package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT am.descripcion AS Comida, SUM(pd.cantidad) AS Pedidos " +
            "FROM Pedido p " +
            "JOIN p.pedidoDetalles pd " +
            "JOIN pd.articulo am " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.eliminado = false " +
            "AND pd.eliminado = false " +
            "GROUP BY am.descripcion " +
            "ORDER BY Pedidos DESC")
    List<Object[]> obtenerRankingComidas(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findPedidosByFechaRange(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT c.nombre, c.apellido, COUNT(p) AS CantidadPedidos " +
            "FROM Pedido p " +
            "JOIN p.cliente c " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY c.nombre, c.apellido " +
            "ORDER BY CantidadPedidos DESC")
    List<Object[]> obtenerCantidadPedidosPorCliente(LocalDate fechaInicio, LocalDate fechaFin);
}
