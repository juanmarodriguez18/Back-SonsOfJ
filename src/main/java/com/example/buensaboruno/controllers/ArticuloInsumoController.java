package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import com.example.buensaboruno.domain.entities.ImagenArticulo;
import com.example.buensaboruno.servicesImpl.ArticuloInsumoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/insumos")
public class ArticuloInsumoController extends BaseControllerImpl<ArticuloInsumo, ArticuloInsumoServiceImpl> {

    protected final ArticuloInsumoServiceImpl service;

    public ArticuloInsumoController(ArticuloInsumoServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<ArticuloInsumo> insumos = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(insumos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los insumos. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ArticuloInsumo insumo) {
        try {
            // Asignar el ArticuloInsumo a cada ImagenArticulo
            for (ImagenArticulo imagenArticulo : insumo.getImagenesArticulo()) {
                imagenArticulo.setArticulo(insumo);
            }

            // Guardar el ArticuloInsumo con sus imagenes
            ArticuloInsumo savedInsumo = service.save(insumo);

            // Retornar la respuesta con el ArticuloInsumo guardado
            return ResponseEntity.status(HttpStatus.OK).body(savedInsumo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el insumo. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ArticuloInsumo insumo) {
        try {
            ArticuloInsumo searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Insumo no encontrado\"}");
            }
            insumo.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(insumo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el insumo. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarArticuloInsumo(@PathVariable Long id) {
        try {
            ArticuloInsumo articuloInsumo = service.findById(id);
            if (articuloInsumo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Insumo no encontrado\"}");
            }
            articuloInsumo.setEliminado(true);
            service.update(articuloInsumo);
            return ResponseEntity.status(HttpStatus.OK).body(articuloInsumo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar el insumo. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarArticuloInsumo(@PathVariable Long id) {
        try {
            ArticuloInsumo articuloInsumo = service.findById(id);
            if (articuloInsumo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Insumo no encontrado\"}");
            }
            articuloInsumo.setEliminado(false);
            service.update(articuloInsumo);
            return ResponseEntity.status(HttpStatus.OK).body(articuloInsumo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar el insumo. Por favor intente luego\"}");
        }
    }

}
