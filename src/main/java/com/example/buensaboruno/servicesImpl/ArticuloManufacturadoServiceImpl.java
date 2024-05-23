package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.ArticuloManufacturado;
import com.example.buensaboruno.repositories.ArticuloManufacturadoRepository;
import com.example.buensaboruno.services.ArticuloManufacturadoService;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoServiceImpl extends BaseServiceImpl<ArticuloManufacturado, Long> implements ArticuloManufacturadoService {

    private ArticuloManufacturadoRepository articuloManufacturadoRepository;
    public ArticuloManufacturadoServiceImpl(ArticuloManufacturadoRepository articuloManufacturadoRepository) {
        super(articuloManufacturadoRepository);
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
    }
}
