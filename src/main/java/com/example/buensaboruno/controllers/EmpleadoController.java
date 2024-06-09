package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.servicesImpl.EmpleadoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/empleados")
public class EmpleadoController extends BaseControllerImpl <Empleado, EmpleadoServiceImpl>{

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


}
