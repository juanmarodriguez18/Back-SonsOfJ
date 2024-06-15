package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import com.example.buensaboruno.domain.entities.ImagenArticulo;
import com.example.buensaboruno.repositories.ArticuloInsumoRepository;
import com.example.buensaboruno.repositories.ImagenArticuloRepository;
import com.example.buensaboruno.services.ArticuloInsumoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticuloInsumoServiceImpl extends BaseServiceImpl<ArticuloInsumo, Long> implements ArticuloInsumoService {

    private final ArticuloInsumoRepository articuloInsumoRepository;
    private final ImagenArticuloRepository imagenArticuloRepository;

    public ArticuloInsumoServiceImpl(ArticuloInsumoRepository articuloInsumoRepository,
                                     ImagenArticuloRepository imagenArticuloRepository) {
        super(articuloInsumoRepository);
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.imagenArticuloRepository = imagenArticuloRepository; // Initialize imagenArticuloRepository
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
        // Guardar el ArticuloInsumo y sus imágenes
        for (ImagenArticulo imagenArticulo : articuloInsumo.getImagenesArticulo()) {
            // Asignar el ArticuloInsumo actualizado a la imagen
            imagenArticulo.setArticulo(articuloInsumo);

            // Verificar si la URL de la imagen ha cambiado
            if (imagenArticulo.getId() != null) {
                ImagenArticulo imagenGuardada = imagenArticuloRepository.findById(imagenArticulo.getId()).orElse(null);
                if (imagenGuardada != null && !imagenGuardada.getUrl().equals(imagenArticulo.getUrl())) {
                    // Actualizar la URL de la imagen
                    imagenGuardada.setUrl(imagenArticulo.getUrl());
                    imagenArticuloRepository.save(imagenGuardada);
                }
            }
        }

        // Guardar el ArticuloInsumo actualizado
        return articuloInsumoRepository.save(articuloInsumo);
    }

}
