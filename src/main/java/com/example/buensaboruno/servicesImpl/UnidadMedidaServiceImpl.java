package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.UnidadMedida;
import com.example.buensaboruno.repositories.UnidadMedidaRepository;
import com.example.buensaboruno.services.UnidadMedidaService;
import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaServiceImpl extends BaseServiceImpl<UnidadMedida, Long> implements UnidadMedidaService {

    private UnidadMedidaRepository unidadMedidaRepository;
    public UnidadMedidaServiceImpl(UnidadMedidaRepository unidadMedidaRepository) {
        super(unidadMedidaRepository);
        this.unidadMedidaRepository = unidadMedidaRepository;
    }
}
