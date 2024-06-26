package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.*;
import com.example.buensaboruno.servicesImpl.EmpleadoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/empleados")
public class EmpleadoController extends BaseControllerImpl<Empleado, EmpleadoServiceImpl> {

    public EmpleadoController(EmpleadoServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Empleado> empleados = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todos los empleados. Por favor intente luego\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Empleado empleado) {
        try {
            Empleado savedEmpleado = service.save(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpleado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar el empleado. Por favor intente luego\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            Empleado updatedEmpleado = service.update(id, empleado);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmpleado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar el empleado. Por favor intente luego\"}");
        }
    }
}
