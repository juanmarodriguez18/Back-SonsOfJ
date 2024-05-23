package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Localidad;
import com.example.buensaboruno.repositories.LocalidadRepository;
import com.example.buensaboruno.services.LocalidadService;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements LocalidadService {

    private LocalidadRepository localidadRepository;
    public LocalidadServiceImpl(LocalidadRepository localidadRepository) {
        super(localidadRepository);
        this.localidadRepository = localidadRepository;
    }
}
