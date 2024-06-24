package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.servicesImpl.SucursalServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/sucursales")
@JsonIgnoreProperties
public class SucursalController extends BaseControllerImpl<Sucursal, SucursalServiceImpl> {

    public SucursalController(SucursalServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Sucursal> sucursales = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las sucursales. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Sucursal sucursal) {
        try {
            sucursal.getImagenesSucursal().forEach(imagenSucursal -> imagenSucursal.setSucursal(sucursal));
            sucursal.getPromociones().forEach(promocion -> promocion.getSucursales().add(sucursal));
            sucursal.getCategorias().forEach(categoria -> categoria.getSucursales().add(sucursal));
            sucursal.getPedidos().forEach(pedido -> pedido.setSucursal(sucursal));
            return ResponseEntity.status(HttpStatus.OK).body(service.save(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la sucursal. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada\"}");
            }
            sucursal.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la sucursal. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = service.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada\"}");
            }
            sucursal.setEliminado(true);
            service.update(sucursal);
            return ResponseEntity.status(HttpStatus.OK).body(sucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la sucursal. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = service.findById(id);
            if (sucursal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada\"}");
            }
            sucursal.setEliminado(false);
            service.update(sucursal);
            return ResponseEntity.status(HttpStatus.OK).body(sucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar la sucursal. Por favor intente luego\"}");
        }
    }
}
