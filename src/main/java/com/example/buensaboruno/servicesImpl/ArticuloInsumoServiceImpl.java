package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import com.example.buensaboruno.domain.entities.ImagenArticulo;
import com.example.buensaboruno.repositories.ArticuloInsumoRepository;
import com.example.buensaboruno.services.ArticuloInsumoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticuloInsumoServiceImpl extends BaseServiceImpl<ArticuloInsumo, Long> implements ArticuloInsumoService {

    private final ArticuloInsumoRepository articuloInsumoRepository;

    public ArticuloInsumoServiceImpl(ArticuloInsumoRepository articuloInsumoRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    @Override
    public ArticuloInsumo save(ArticuloInsumo articuloInsumo) {
        // Guardar el ArticuloInsumo y sus imágenes
        for (ImagenArticulo imagenArticulo : articuloInsumo.getImagenesArticulo()) {
            imagenArticulo.setArticulo(articuloInsumo);
        }
        return articuloInsumoRepository.save(articuloInsumo);
    }

    @Override
    public ArticuloInsumo update(ArticuloInsumo articuloInsumo) {
        return articuloInsumoRepository.save(articuloInsumo);
    }
}
