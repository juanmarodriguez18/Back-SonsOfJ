package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.ImagenCliente;
import com.example.buensaboruno.domain.entities.UsuarioCliente;
import com.example.buensaboruno.repositories.ImagenClienteRepository;
import com.example.buensaboruno.repositories.UsuarioClienteRepository;
import com.example.buensaboruno.servicesImpl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteServiceImpl> {

    public ClienteController(ClienteServiceImpl service) {
        super(service);

    }

    @Autowired
    private ImagenClienteRepository imagenClienteRepository;
    @Autowired
    private UsuarioClienteRepository usuarioClienteRepository;

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Cliente> clientes = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los clientes. Por favor intente luego\"}");
        }
    }


    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {
        try {
            ImagenCliente img = imagenClienteRepository.save(cliente.getImagenCliente());
            cliente.setImagenCliente(img);
            UsuarioCliente ucli = usuarioClienteRepository.save(cliente.getUsuarioCliente());
            cliente.setUsuarioCliente(ucli);
            return ResponseEntity.status(HttpStatus.OK).body(service.save(cliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el cliente. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(cliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el cliente. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            Cliente Cliente = service.findById(id);
            if (Cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Categoría no encontrada\"}");
            }
            Cliente.setEliminado(true);
            service.update(Cliente);
            return ResponseEntity.status(HttpStatus.OK).body(Cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la categoría. Por favor intente luego\"}");
        }
    }
}
