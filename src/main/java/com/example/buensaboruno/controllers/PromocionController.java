package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Promocion;
import com.example.buensaboruno.servicesImpl.PromocionServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/promociones")
@JsonIgnoreProperties
public class PromocionController extends BaseControllerImpl<Promocion, PromocionServiceImpl> {

    public PromocionController(PromocionServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Promocion> promociones = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(promociones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las promociones. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Promocion promocion) {
        try {
            promocion.getImagenesPromocion().forEach(imagenPromocion -> imagenPromocion.setPromocion(promocion));
            promocion.getSucursales().forEach(sucursal -> sucursal.getPromociones().add(promocion));
            return ResponseEntity.status(HttpStatus.OK).body(service.save(promocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la promoción. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {
            Promocion searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Promoción no encontrada\"}");
            }
            promocion.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(promocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la promoción. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarPromocion(@PathVariable Long id) {
        try {
            Promocion promocion = service.findById(id);
            if (promocion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Promoción no encontrada\"}");
            }
            promocion.setEliminado(true);
            service.update(promocion);
            return ResponseEntity.status(HttpStatus.OK).body(promocion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la promoción. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarPromocion(@PathVariable Long id) {
        try {
            Promocion promocion = service.findById(id);
            if (promocion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Promoción no encontrada\"}");
            }
            promocion.setEliminado(false);
            service.update(promocion);
            return ResponseEntity.status(HttpStatus.OK).body(promocion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar la promoción. Por favor intente luego\"}");
        }
    }
}
