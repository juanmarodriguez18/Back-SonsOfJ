package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Pais;
import com.example.buensaboruno.domain.entities.Pais;
import com.example.buensaboruno.servicesImpl.PaisServiceImpl;
import com.example.buensaboruno.servicesImpl.PaisServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/paises")
public class PaisController extends BaseControllerImpl<Pais, PaisServiceImpl> {
    
    public PaisController(PaisServiceImpl service) {
        super(service);

    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Pais> paises = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(paises);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las Paises. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Pais pais) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(pais));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la Pais. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pais pais) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(pais));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la Pais. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> eliminarPais(@PathVariable Long id) {
        try {
            Pais pais = service.findById(id);
            if (pais == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Pais no encontrada\"}");
            }
            pais.setEliminado(true);
            service.update(pais);
            return ResponseEntity.status(HttpStatus.OK).body(pais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la Pais. Por favor intente luego\"}");
        }
    }
}
