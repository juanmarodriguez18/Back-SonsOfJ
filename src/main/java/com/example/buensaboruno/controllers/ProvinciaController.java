package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Provincia;
import com.example.buensaboruno.servicesImpl.ProvinciaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/provincias")
public class ProvinciaController extends BaseControllerImpl<Provincia, ProvinciaServiceImpl> {

    public ProvinciaController(ProvinciaServiceImpl service) {
        super(service);

    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Provincia> provincias = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(provincias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las Provinciaes. Por favor intente luego\"}");
        }
    }

    @GetMapping("/de/{pais}")
    public ResponseEntity<?> findByProvincia(@PathVariable String pais) {
        try {
            List<Provincia> provincias = service.findByPais(pais);
            return ResponseEntity.status(HttpStatus.OK).body(provincias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las Provinciaes. Por favor intente luego\"}");
        }
    }


    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Provincia provincia) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(provincia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la Provincia. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Provincia provincia) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(provincia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la Provincia. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> eliminarProvincia(@PathVariable Long id) {
        try {
            Provincia provincia = service.findById(id);
            if (provincia == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Provincia no encontrada\"}");
            }
            provincia.setEliminado(true);
            service.update(provincia);
            return ResponseEntity.status(HttpStatus.OK).body(provincia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la Provincia. Por favor intente luego\"}");
        }
    }
}
