package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface PedidoService extends BaseService<Pedido, Long> {
    List<Pedido> findPedidosByFecha(LocalDate fechaInicio, LocalDate fechaFin);
}
