package com.example.buensaboruno.controller;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/pedidos")
    public List<Pedido> getPedidosByFecha(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerPedidosPorFecha(inicio, fin);
    }

    @GetMapping("/ranking-comidas")
    public List<Object[]> getRankingComidas(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerRankingComidas(inicio, fin);
    }

    @GetMapping("/cantidad-pedidos-cliente")
    public List<Object[]> getCantidadPedidosPorCliente(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerCantidadPedidosPorCliente(inicio, fin);
    }
}
