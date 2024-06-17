package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.ArticuloManufacturado;
import com.example.buensaboruno.domain.entities.ArticuloManufacturadoDetalle;
import com.example.buensaboruno.domain.entities.ImagenArticulo;
import com.example.buensaboruno.repositories.ArticuloManufacturadoRepository;
import com.example.buensaboruno.repositories.ArticuloManufacturadoDetalleRepository;
import com.example.buensaboruno.repositories.ImagenArticuloRepository;
import com.example.buensaboruno.services.ArticuloManufacturadoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticuloManufacturadoServiceImpl extends BaseServiceImpl<ArticuloManufacturado, Long> implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepository articuloManufacturadoRepository;
    private final ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;
    private final ImagenArticuloRepository imagenArticuloRepository;

    public ArticuloManufacturadoServiceImpl(ArticuloManufacturadoRepository articuloManufacturadoRepository,
                                            ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository,
                                            ImagenArticuloRepository imagenArticuloRepository) {
        super(articuloManufacturadoRepository);
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.articuloManufacturadoDetalleRepository = articuloManufacturadoDetalleRepository;
        this.imagenArticuloRepository = imagenArticuloRepository;
    }

    @Override
    public ArticuloManufacturado save(ArticuloManufacturado articulo) {
        Set<ImagenArticulo> nuevasImagenes = articulo.getImagenesArticulo().stream()
                .peek(imagen -> imagen.setArticulo(articulo))
                .collect(Collectors.toSet());
        articulo.setImagenesArticulo(nuevasImagenes);

        Set<ArticuloManufacturadoDetalle> nuevosDetalles = articulo.getArticuloManufacturadoDetalles().stream()
                .peek(detalle -> detalle.setArticuloManufacturado(articulo))
                .collect(Collectors.toSet());
        articulo.setArticuloManufacturadoDetalles(nuevosDetalles);
        return articuloManufacturadoRepository.save(articulo);
    }

    @Override
    public ArticuloManufacturado update(ArticuloManufacturado articulo) {
        // Guardar el Articulo y sus imágenes
        for (ImagenArticulo imagenArticulo : articulo.getImagenesArticulo()) {
            // Asignar el Articulo actualizado a la imagen
            imagenArticulo.setArticulo(articulo);

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
        // Guardar el Articulo y sus detalles
        for (ArticuloManufacturadoDetalle detalle : articulo.getArticuloManufacturadoDetalles()) {
            // Asignar el Articulo actualizado al detalle
            detalle.setArticuloManufacturado(articulo);

            // Verificar si el detalle ya existe en la base de datos
            if (detalle.getId() != null) {
                ArticuloManufacturadoDetalle detalleGuardado = articuloManufacturadoDetalleRepository.findById(detalle.getId()).orElse(null);
                if (detalleGuardado != null) {
                    // Actualizar la cantidad y el insumo si han cambiado
                    if (detalle.getCantidad() != detalleGuardado.getCantidad() || !detalle.getArticuloInsumo().equals(detalleGuardado.getArticuloInsumo())) {
                        detalleGuardado.setCantidad(detalle.getCantidad());
                        detalleGuardado.setArticuloInsumo(detalle.getArticuloInsumo());
                        articuloManufacturadoDetalleRepository.save(detalleGuardado);
                    }
                }
            }
        }


        return articuloManufacturadoRepository.save(articulo);
    }
}
