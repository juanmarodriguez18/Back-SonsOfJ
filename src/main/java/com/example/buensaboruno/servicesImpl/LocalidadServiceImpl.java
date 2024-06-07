package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Localidad;
import com.example.buensaboruno.repositories.LocalidadRepository;
import com.example.buensaboruno.services.LocalidadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements LocalidadService {

    private LocalidadRepository localidadRepository;
    public LocalidadServiceImpl(LocalidadRepository localidadRepository) {
        super(localidadRepository);
        this.localidadRepository = localidadRepository;
    }

    public List<Localidad> findByProvincia(String provincia) {
        return localidadRepository.findByProvincia(provincia);
    }
    public Localidad findByNombre(String nombre) {
        return localidadRepository.findByNombre(nombre);
    }
}
