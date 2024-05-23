package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Domicilio;
import com.example.buensaboruno.repositories.DomicilioRepository;
import com.example.buensaboruno.services.DomicilioService;
import org.springframework.stereotype.Service;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<Domicilio, Long> implements DomicilioService {

    private DomicilioRepository domicilioRepository;
    public DomicilioServiceImpl(DomicilioRepository domicilioRepository) {
        super(domicilioRepository);
        this.domicilioRepository = domicilioRepository;
    }
}
