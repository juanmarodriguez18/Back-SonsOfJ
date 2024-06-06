package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.servicesImpl.PedidoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController extends BaseControllerImpl<Pedido, PedidoServiceImpl> {

    public PedidoController(PedidoServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Pedido> pedidos = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(pedidos);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los pedidos. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Pedido pedido) {
        try {

            //pedido.getPedidoDetalles().forEach(pedidoDetalle -> pedidoDetalle.getArticulo().getId());
            System.out.println(pedido.getCliente().getId());
            return ResponseEntity.status(HttpStatus.OK).body(service.save(pedido));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el pedido. Por favor intente luego\"}");
        }
    }
}
