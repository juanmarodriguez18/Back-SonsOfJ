package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Pedido;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    ByteArrayInputStream generarReporteRankingComidas(List<Object[]> datos) throws IOException;
    ByteArrayInputStream generarReporteIngresosDiarios(List<Object[]> datos) throws IOException;
    ByteArrayInputStream generarReporteIngresosMensuales(List<Object[]> datos) throws IOException;
    ByteArrayInputStream generarReportePedidosPorCliente(List<Object[]> datos) throws IOException;
    ByteArrayInputStream generarReporteGanancia(Double ganancia) throws IOException;
    ByteArrayInputStream generarReportePedidos(List<Pedido> pedidos) throws IOException;
}
