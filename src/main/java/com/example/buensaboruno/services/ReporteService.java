package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<Pedido> obtenerPedidosPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<Object[]> obtenerRankingComidas(LocalDate fechaInicio, LocalDate fechaFin);
    List<Object[]> obtenerCantidadPedidosPorCliente(LocalDate fechaInicio, LocalDate fechaFin);
}
