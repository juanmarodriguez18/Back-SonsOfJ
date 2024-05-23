package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.repositories.SucursalRepository;
import com.example.buensaboruno.services.SucursalService;
import org.springframework.stereotype.Service;

@Service
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal, Long> implements SucursalService {

    private SucursalRepository sucursalRepository;
    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        super(sucursalRepository);
        this.sucursalRepository = sucursalRepository;
    }
}
