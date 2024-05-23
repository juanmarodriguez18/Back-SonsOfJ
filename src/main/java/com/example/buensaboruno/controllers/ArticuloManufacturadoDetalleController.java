package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.ArticuloManufacturadoDetalle;
import com.example.buensaboruno.repositories.ArticuloManufacturadoDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticuloManufacturadoDetalleController {

    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @GetMapping("/articulos-detalle")
    public List<ArticuloManufacturadoDetalle> getAllArticuloManufacturadoDetalle() {
        return articuloManufacturadoDetalleRepository.findAll();
    }

    @GetMapping("/articulos-detalle/{id}")
    public ArticuloManufacturadoDetalle getArticuloManufacturadoDetalleById(@PathVariable long id) {
        return articuloManufacturadoDetalleRepository.findById(id).orElse(null);
    }

    @PostMapping("/articulos-detalle")
    public ArticuloManufacturadoDetalle crearArticuloManufacturadoDetalle(@RequestBody ArticuloManufacturadoDetalle nuevoDetalle){
        return articuloManufacturadoDetalleRepository.save(nuevoDetalle);
    }

    @PatchMapping("/articulos-detalle/{id}")
    public void eliminarArticuloManufacturadoDetalle(@PathVariable Long id) {
        ArticuloManufacturadoDetalle detalle = articuloManufacturadoDetalleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado con id: " + id));
        detalle.setEliminado(true);
        articuloManufacturadoDetalleRepository.save(detalle);
    }


    @PutMapping("/articulos-detalle/{id}")
    public ArticuloManufacturadoDetalle actualizarArticuloManufacturadoDetalle(@PathVariable Long id, @RequestBody ArticuloManufacturadoDetalle datosActualizados) {
        ArticuloManufacturadoDetalle detalle = articuloManufacturadoDetalleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalle no encontrado con id: " + id));
        detalle.setCantidad(datosActualizados.getCantidad());
        // Actualiza otros atributos según sea necesario
        return articuloManufacturadoDetalleRepository.save(detalle);
    }

    // Endpoint para obtener el último id
    @GetMapping("/articulos-detalle/ultimoId")
    public ResponseEntity<Long> obtenerUltimoId() {
        Long ultimoId = articuloManufacturadoDetalleRepository.obtenerUltimoId(); // Implementa este método en tu servicio
        return ResponseEntity.ok(ultimoId);
    }
}
