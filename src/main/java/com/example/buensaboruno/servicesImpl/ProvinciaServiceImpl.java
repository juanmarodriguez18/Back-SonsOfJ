package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Provincia;
import com.example.buensaboruno.repositories.ProvinciaRepository;
import com.example.buensaboruno.services.ProvinciaService;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaServiceImpl extends BaseServiceImpl<Provincia, Long> implements ProvinciaService {

    private ProvinciaRepository provinciaRepository;
    public ProvinciaServiceImpl(ProvinciaRepository provinciaRepository) {
        super(provinciaRepository);
        this.provinciaRepository = provinciaRepository;
    }
}
