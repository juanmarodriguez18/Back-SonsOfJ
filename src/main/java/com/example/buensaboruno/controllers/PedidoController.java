package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.enums.Estado;
import com.example.buensaboruno.servicesImpl.PedidoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {

    private final PedidoServiceImpl service;

    public PedidoController(PedidoServiceImpl service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Pedido> pedidos = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los pedidos. Por favor intente luego\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody Pedido pedido) {
        try {
            Pedido savedPedido = service.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el pedido. Por favor intente luego\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar el pedido. Por favor intente luego\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        try {
            Pedido updatedPedido = service.update(pedido, id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el pedido. Por favor intente luego\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Pedido pedido = service.findById(id);
            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Pedido no encontrado\"}");
            }
            return ResponseEntity.status(HttpStatus.OK).body(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener el pedido. Por favor intente luego\"}");
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> updateEstado(@PathVariable Long id, @RequestBody String estadoString) {
        try {
            Estado estado = Estado.valueOf(estadoString); // Convertir el String a enum Estado
            Pedido pedido = service.findById(id);
            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Pedido no encontrado\"}");
            }
            pedido.setEstado(estado);
            service.update(pedido); // No es necesario pasar el id como parámetro, ya que ya se tiene el pedido con ese id.
            return ResponseEntity.status(HttpStatus.OK).body(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Estado inválido: " + estadoString + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el estado del pedido. Por favor intente luego\"}");
        }
    }


}
