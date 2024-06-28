package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.services.ExcelService;
import com.example.buensaboruno.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ExcelService excelService;

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

    @GetMapping("/ingresos-diarios")
    public List<Object[]> getIngresosDiarios(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerIngresosDiarios(inicio, fin);
    }

    @GetMapping("/ingresos-mensuales")
    public List<Object[]> getIngresosMensuales(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerIngresosMensuales(inicio, fin);
    }

    @GetMapping("/ganancia")
    public Double getGanancia(@RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return reporteService.obtenerGanancia(inicio, fin);
    }

    @GetMapping("/excel/ranking-comidas")
    public ResponseEntity<InputStreamResource> descargarExcelRankingComidas(@RequestParam String fechaInicio, @RequestParam String fechaFin) throws IOException {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Object[]> datos = reporteService.obtenerRankingComidas(inicio, fin);
        ByteArrayInputStream in = excelService.generarReporteRankingComidas(datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ranking_comidas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/excel/ingresos-diarios")
    public ResponseEntity<InputStreamResource> descargarExcelIngresosDiarios(@RequestParam String fechaInicio, @RequestParam String fechaFin) throws IOException {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Object[]> datos = reporteService.obtenerIngresosDiarios(inicio, fin);
        ByteArrayInputStream in = excelService.generarReporteIngresosDiarios(datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ingresos_diarios.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/excel/ingresos-mensuales")
    public ResponseEntity<InputStreamResource> descargarExcelIngresosMensuales(@RequestParam String fechaInicio, @RequestParam String fechaFin) throws IOException {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Object[]> datos = reporteService.obtenerIngresosMensuales(inicio, fin);
        ByteArrayInputStream in = excelService.generarReporteIngresosMensuales(datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ingresos_mensuales.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/excel/pedidos-cliente")
    public ResponseEntity<InputStreamResource> descargarExcelPedidosPorCliente(@RequestParam String fechaInicio, @RequestParam String fechaFin) throws IOException {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        List<Object[]> datos = reporteService.obtenerCantidadPedidosPorCliente(inicio, fin);
        ByteArrayInputStream in = excelService.generarReportePedidosPorCliente(datos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=pedidos_cliente.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping("/excel/ganancia")
    public ResponseEntity<InputStreamResource> descargarExcelGanancia(@RequestParam String fechaInicio, @RequestParam String fechaFin) throws IOException {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        Double ganancia = reporteService.obtenerGanancia(inicio, fin);
        ByteArrayInputStream in = excelService.generarReporteGanancia(ganancia);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=ganancia.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
