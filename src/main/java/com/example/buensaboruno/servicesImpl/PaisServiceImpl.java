package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Pais;
import com.example.buensaboruno.repositories.PaisRepository;
import com.example.buensaboruno.services.PaisService;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl extends BaseServiceImpl<Pais, Long> implements PaisService {

    private PaisRepository paisRepository;

    public PaisServiceImpl(PaisRepository paisRepository) {
        super(paisRepository);
        this.paisRepository = paisRepository;
    }

}