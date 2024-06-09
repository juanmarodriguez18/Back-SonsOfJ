package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Factura;
import com.example.buensaboruno.repositories.FacturaRepository;
import com.example.buensaboruno.services.FacturaService;
import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements FacturaService {

    private FacturaRepository facturaRepository;
    public FacturaServiceImpl(FacturaRepository facturaRepository) {
        super(facturaRepository);
        this.facturaRepository = facturaRepository;
    }

}
