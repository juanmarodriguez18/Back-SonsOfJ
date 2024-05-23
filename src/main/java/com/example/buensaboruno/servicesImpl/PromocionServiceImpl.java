package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Promocion;
import com.example.buensaboruno.repositories.PromocionRepository;
import com.example.buensaboruno.services.PromocionService;
import org.springframework.stereotype.Service;

@Service
public class PromocionServiceImpl extends BaseServiceImpl<Promocion, Long> implements PromocionService {

    private PromocionRepository promocionRepository;
    public PromocionServiceImpl(PromocionRepository promocionRepository) {
        super(promocionRepository);
        this.promocionRepository = promocionRepository;
    }
}
