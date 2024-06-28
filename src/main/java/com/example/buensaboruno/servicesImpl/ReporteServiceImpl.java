package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.repositories.ReporteRepository;
import com.example.buensaboruno.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public List<Pedido> obtenerPedidosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return reporteRepository.findPedidosByFechaRange(fechaInicio, fechaFin);
    }

    @Override
    public List<Object[]> obtenerRankingComidas(LocalDate fechaInicio, LocalDate fechaFin) {
        return reporteRepository.obtenerRankingComidas(fechaInicio, fechaFin);
    }
}

