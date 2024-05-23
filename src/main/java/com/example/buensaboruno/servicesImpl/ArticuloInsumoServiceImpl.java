package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import com.example.buensaboruno.repositories.ArticuloInsumoRepository;
import com.example.buensaboruno.services.ArticuloInsumoService;
import org.springframework.stereotype.Service;

@Service
public class ArticuloInsumoServiceImpl extends BaseServiceImpl<ArticuloInsumo, Long> implements ArticuloInsumoService {

    private ArticuloInsumoRepository articuloInsumoRepository;

    public ArticuloInsumoServiceImpl(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }
}
