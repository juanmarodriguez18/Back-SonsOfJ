package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.servicesImpl.SucursalServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Sucursal sucursal = service.findById(id);
            if (sucursal != null) {
                return ResponseEntity.status(HttpStatus.OK).body(sucursal);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Sucursal no encontrada\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener la sucursal. Por favor intente luego\"}");
        }
    }

    // Método para obtener los pedidos de una sucursal específica
    @GetMapping("/{id}/pedidos")
    public ResponseEntity<?> getPedidosBySucursal(@PathVariable Long id) {
        try {
            Set<Pedido> pedidos = service.getPedidosBySucursal(id);
            return ResponseEntity.status(HttpStatus.OK).body(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener los pedidos de la sucursal. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Sucursal sucursal) {
        try {
            service.save(sucursal);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Sucursal guardada exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> findByEmpresa(@PathVariable Long empresaId) {
        try {
            List<Sucursal> sucursales = service.findByEmpresa(empresaId);
            return ResponseEntity.status(HttpStatus.OK).body(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las sucursales. Por favor intente luego\"}");
        }
    }
}
