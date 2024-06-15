package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.*;
import com.example.buensaboruno.servicesImpl.ArticuloManufacturadoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/articulos-manufacturados")
public class ArticuloManufacturadoController extends BaseControllerImpl<ArticuloManufacturado, ArticuloManufacturadoServiceImpl> {

    public ArticuloManufacturadoController(ArticuloManufacturadoServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<ArticuloManufacturado> articulos = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(articulos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los artículos manufacturados. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ArticuloManufacturado articulo) {
        try {
            ArticuloManufacturado savedArticulo = service.save(articulo);
            return ResponseEntity.status(HttpStatus.OK).body(savedArticulo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el artículo manufacturado. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ArticuloManufacturado articulo) {
        try {
            ArticuloManufacturado updatedArticulo = service.update(articulo);
            return ResponseEntity.status(HttpStatus.OK).body(updatedArticulo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Artículo manufacturado no encontrado\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el artículo manufacturado. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarArticuloManufacturado(@PathVariable Long id) {
        try {
            ArticuloManufacturado articuloManufacturado = service.findById(id);
            if (articuloManufacturado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Artículo manufacturado no encontrado\"}");
            }
            articuloManufacturado.setEliminado(true);
            service.update(articuloManufacturado);
            return ResponseEntity.status(HttpStatus.OK).body(articuloManufacturado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar el artículo manufacturado. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarArticuloManufacturado(@PathVariable Long id) {
        try {
            ArticuloManufacturado articuloManufacturado = service.findById(id);
            if (articuloManufacturado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Artículo manufacturado no encontrado\"}");
            }
            articuloManufacturado.setEliminado(false);
            service.update(articuloManufacturado);
            return ResponseEntity.status(HttpStatus.OK).body(articuloManufacturado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar el artículo manufacturado. Por favor intente luego\"}");
        }
    }
}
