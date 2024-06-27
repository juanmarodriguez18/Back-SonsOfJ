package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.repositories.SucursalRepository;
import com.example.buensaboruno.services.SucursalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal, Long> implements SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        super(sucursalRepository);
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public Sucursal findById(Long id) throws Exception {
        return sucursalRepository.findById(id).orElse(null);
    }

    // Método para obtener los pedidos de una sucursal específica
    public Set<Pedido> getPedidosBySucursal(Long sucursalId) throws Exception {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
        if (sucursalOptional.isPresent()) {
            Sucursal sucursal = sucursalOptional.get();
            return sucursal.getPedidos(); // Retorna la lista de pedidos asociados a la sucursal
        } else {
            throw new Exception("Sucursal no encontrada con ID: " + sucursalId);
        }
    }
}
