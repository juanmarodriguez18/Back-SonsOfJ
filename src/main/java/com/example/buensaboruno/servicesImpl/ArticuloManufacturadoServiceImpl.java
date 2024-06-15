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
        configurarRelaciones(articulo);
        return articuloManufacturadoRepository.save(articulo);
    }

    @Override
    public ArticuloManufacturado update(ArticuloManufacturado articulo) {
        ArticuloManufacturado searchedEntity = articuloManufacturadoRepository.findById(articulo.getId())
                .orElseThrow(() -> new EntityNotFoundException("Artículo manufacturado no encontrado"));


        // Actualizar las imágenes
        actualizarImagenes(articulo, searchedEntity);

        // Actualizar los detalles
        actualizarDetalles(articulo, searchedEntity);

        // Actualizar las propiedades del artículo
        searchedEntity.setDenominacion(articulo.getDenominacion());
        searchedEntity.setDescripcion(articulo.getDescripcion());
        searchedEntity.setTiempoEstimadoMinutos(articulo.getTiempoEstimadoMinutos());
        searchedEntity.setPrecioVenta(articulo.getPrecioVenta());

        return articuloManufacturadoRepository.save(searchedEntity);
    }

    private void actualizarImagenes(ArticuloManufacturado articulo, ArticuloManufacturado searchedEntity) {
        Set<ImagenArticulo> nuevasImagenes = new HashSet<>();

        for (ImagenArticulo nuevaImagen : articulo.getImagenesArticulo()) {
            Optional<ImagenArticulo> imagenGuardada = imagenArticuloRepository.findById(nuevaImagen.getId());
            if (imagenGuardada.isPresent()) {
                if (!imagenGuardada.get().getUrl().equals(nuevaImagen.getUrl())) {
                    imagenGuardada.get().setUrl(nuevaImagen.getUrl());
                    imagenArticuloRepository.save(imagenGuardada.get());
                }
                nuevasImagenes.add(imagenGuardada.get());
            } else {
                nuevaImagen.setArticulo(searchedEntity);
                nuevasImagenes.add(nuevaImagen);
            }
        }

        searchedEntity.setImagenesArticulo(nuevasImagenes);
    }

    private void actualizarDetalles(ArticuloManufacturado articulo, ArticuloManufacturado searchedEntity) {
        Set<ArticuloManufacturadoDetalle> nuevosDetalles = new HashSet<>();

        for (ArticuloManufacturadoDetalle nuevoDetalle : articulo.getArticuloManufacturadoDetalles()) {
            Optional<ArticuloManufacturadoDetalle> detalleGuardado = articuloManufacturadoDetalleRepository.findById(nuevoDetalle.getId());
            if (detalleGuardado.isPresent()) {
                detalleGuardado.get().setArticuloInsumo(nuevoDetalle.getArticuloInsumo());
                detalleGuardado.get().setCantidad(nuevoDetalle.getCantidad());
                articuloManufacturadoDetalleRepository.save(detalleGuardado.get());
                nuevosDetalles.add(detalleGuardado.get());
            } else {
                nuevoDetalle.setArticuloManufacturado(searchedEntity);
                articuloManufacturadoDetalleRepository.save(nuevoDetalle);
                nuevosDetalles.add(nuevoDetalle);
            }
        }

        searchedEntity.setArticuloManufacturadoDetalles(nuevosDetalles);
    }

    private void configurarRelaciones(ArticuloManufacturado articulo) {
        Set<ImagenArticulo> nuevasImagenes = articulo.getImagenesArticulo().stream()
                .peek(imagen -> imagen.setArticulo(articulo))
                .collect(Collectors.toSet());
        articulo.setImagenesArticulo(nuevasImagenes);

        Set<ArticuloManufacturadoDetalle> nuevosDetalles = articulo.getArticuloManufacturadoDetalles().stream()
                .peek(detalle -> detalle.setArticuloManufacturado(articulo))
                .collect(Collectors.toSet());
        articulo.setArticuloManufacturadoDetalles(nuevosDetalles);
    }
}
