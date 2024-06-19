package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Categoria;
import com.example.buensaboruno.domain.entities.UnidadMedida;
import com.example.buensaboruno.servicesImpl.CategoriaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;


@RestController
@RequestMapping(path = "/categorias")
public class CategoriaController extends BaseControllerImpl<Categoria, CategoriaServiceImpl> {

    public CategoriaController(CategoriaServiceImpl service) {
        super(service);

    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<Categoria> categorias = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al obtener todas las categorías. Por favor intente luego\"}");
        }
    }


    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Categoria categoria) {
        try {
            categoria.getSubCategorias().forEach(subcategoria -> subcategoria.setCategoriaPadre(categoria));
            return ResponseEntity.status(HttpStatus.OK).body(service.save(categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al guardar la categoría. Por favor intente luego\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            categoria.getSubCategorias().forEach(subcategoria -> subcategoria.setCategoriaPadre(categoria));
            Categoria searchedEntity = service.findById(id);
            if (searchedEntity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Categoría no encontrada\"}");
            }
            categoria.setId(id);
            categoria.setCategoriaPadre(searchedEntity.getCategoriaPadre());
            return ResponseEntity.status(HttpStatus.OK).body(service.update(categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al actualizar la categoría. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            Categoria categoria = service.findById(id);
            if (categoria == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Categoria no encontrada\"}");
            }

            categoria.setEliminado(true);
            service.update(categoria);

            // Devolver la categoria actualizada
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al eliminar la categoria. Por favor intente luego\"}");
        }
    }

    @PatchMapping("/{id}/recuperar")
    public ResponseEntity<?> recuperarCategoria(@PathVariable Long id) {
        try {
            Categoria categoria = service.findById(id);
            if (categoria == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Categoria no encontrada\"}");
            }

            categoria.setEliminado(false);
            service.update(categoria);

            // Devolver la categoria actualizada
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error al recuperar la categoria. Por favor intente luego\"}");
        }
    }
}
