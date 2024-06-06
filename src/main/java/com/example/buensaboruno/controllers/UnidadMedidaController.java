package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.UnidadMedida;
import com.example.buensaboruno.servicesImpl.UnidadMedidaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/unidad-medida")
public class UnidadMedidaController extends BaseControllerImpl<UnidadMedida, UnidadMedidaServiceImpl> {

    public UnidadMedidaController(UnidadMedidaServiceImpl service) {
        super(service);
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<UnidadMedida> unidadesMedida = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(unidadesMedida);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las unidades de medida. Por favor intente luego\"}");
        }
    }


    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UnidadMedida unidadMedida) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(unidadMedida));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la unidad de medida. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UnidadMedida unidadMedida) {
        try {
            UnidadMedida searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Unidad de medida no encontrada\"}");
            }
            unidadMedida.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.update(unidadMedida));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la unidad de medida. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarUnidadMedida(@PathVariable Long id) {
        try {
            UnidadMedida unidadMedida = service.findById(id);
            if (unidadMedida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Unidad de medida no encontrada\"}");
            }

            unidadMedida.setEliminado(true);
            service.update(unidadMedida);

            // Devolver la unidad de medida actualizada
            return ResponseEntity.status(HttpStatus.OK).body(unidadMedida);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la unidad de medida. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarUnidadMedida(@PathVariable Long id) {
        try {
            UnidadMedida unidadMedida = service.findById(id);
            if (unidadMedida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Unidad de medida no encontrada\"}");
            }

            unidadMedida.setEliminado(false);
            service.update(unidadMedida);

            // Devolver la unidad de medida actualizada
            return ResponseEntity.status(HttpStatus.OK).body(unidadMedida);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar la unidad de medida. Por favor intente luego\"}");
        }
    }

    /*@PatchMapping("/{id}")
    public ResponseEntity<?> eliminarUnidadMedida(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        try {
            UnidadMedida unidadMedida = service.findById(id);
            if (unidadMedida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Unidad de medida no encontrada\"}");
            }

            Boolean eliminado = body.get("eliminado");
            if (eliminado == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El campo 'eliminado' es requerido\"}");
            }

            unidadMedida.setEliminado(eliminado);
            service.update(unidadMedida);

            // Devolver la unidad de medida actualizada
            return ResponseEntity.status(HttpStatus.OK).body(unidadMedida);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la unidad de medida. Por favor intente luego\"}");
        }
    }*/

}
