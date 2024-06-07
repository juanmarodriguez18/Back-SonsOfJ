package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Localidad;
import com.example.buensaboruno.servicesImpl.LocalidadServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadServiceImpl> {

    public LocalidadController(LocalidadServiceImpl service) {
        super(service);

    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Localidad> localidades = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(localidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las localidades. Por favor intente luego\"}");
        }
    }

    @GetMapping("/con/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
        try {
            Localidad localidad = service.findByNombre(nombre);
            return ResponseEntity.status(HttpStatus.OK).body(localidad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las localidades. Por favor intente luego\"}");
        }
    }

    @GetMapping("/de/{provincia}")
    public ResponseEntity<?> findByProvincia(@PathVariable String provincia) {
        try {
            List<Localidad> localidades = service.findByProvincia(provincia);
            return ResponseEntity.status(HttpStatus.OK).body(localidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos las localidades. Por favor intente luego\"}");
        }
    }


    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Localidad localidad) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(localidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la localidad. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Localidad localidad) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(localidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la localidad. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> eliminarLocalidad(@PathVariable Long id) {
        try {
            Localidad localidad = service.findById(id);
            if (localidad == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"localidad no encontrada\"}");
            }
            localidad.setEliminado(true);
            service.update(localidad);
            return ResponseEntity.status(HttpStatus.OK).body(localidad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la localidad. Por favor intente luego\"}");
        }
    }
}
