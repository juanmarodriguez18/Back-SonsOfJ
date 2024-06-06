package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Empresa;
import com.example.buensaboruno.servicesImpl.EmpresaServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/empresas")
@JsonIgnoreProperties
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaServiceImpl> {

    public EmpresaController(EmpresaServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Empresa> empresas = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(empresas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las empresas. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Empresa empresa) {
        try {
            empresa.getImagenesEmpresa().forEach(imagenEmpresa -> imagenEmpresa.setEmpresa(empresa));
            empresa.getSucursales().forEach(sucursal -> sucursal.setEmpresa(empresa));
            return ResponseEntity.status(HttpStatus.OK).body(service.save(empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la empresa. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Empresa empresa) {
        try {
            Empresa searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Empresa no encontrada\"}");
            }
            empresa.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la empresa. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {
        try {
            Empresa empresa = service.findById(id);
            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Empresa no encontrada\"}");
            }
            empresa.setEliminado(true);
            service.update(empresa);
            return ResponseEntity.status(HttpStatus.OK).body(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la empresa. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarEmpresa(@PathVariable Long id) {
        try {
            Empresa empresa = service.findById(id);
            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Empresa no encontrada\"}");
            }
            empresa.setEliminado(false);
            service.update(empresa);
            return ResponseEntity.status(HttpStatus.OK).body(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar la empresa. Por favor intente luego\"}");
        }
    }
}
