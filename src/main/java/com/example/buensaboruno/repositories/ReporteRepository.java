package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT a.denominacion AS Comida, SUM(pd.cantidad) AS Pedidos " +
            "FROM Pedido p " +
            "JOIN p.pedidoDetalles pd " +
            "JOIN pd.articulo a " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.eliminado = false " +
            "AND pd.eliminado = false " +
            "GROUP BY a.denominacion " +
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

    @Query("SELECT CAST(p.fechaPedido AS java.sql.Date) AS Dia, SUM(p.total) AS Ingresos " +
            "FROM Pedido p " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.eliminado = false " +
            "GROUP BY CAST(p.fechaPedido AS java.sql.Date) " +
            "ORDER BY CAST(p.fechaPedido AS java.sql.Date)")
    List<Object[]> obtenerIngresosDiarios(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT YEAR(p.fechaPedido) AS Anio, MONTH(p.fechaPedido) AS Mes, SUM(p.total) AS Ingresos " +
            "FROM Pedido p " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.eliminado = false " +
            "GROUP BY YEAR(p.fechaPedido), MONTH(p.fechaPedido) " +
            "ORDER BY YEAR(p.fechaPedido), MONTH(p.fechaPedido)")
    List<Object[]> obtenerIngresosMensuales(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT SUM(p.total) - SUM(p.totalCosto) AS Ganancia " +
            "FROM Pedido p " +
            "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
            "AND p.eliminado = false")
    Double obtenerGanancia(LocalDate fechaInicio, LocalDate fechaFin);
}
