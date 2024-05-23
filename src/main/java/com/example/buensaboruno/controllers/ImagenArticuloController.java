package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.ImagenArticulo;
import com.example.buensaboruno.repositories.ImagenArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ImagenArticuloController {

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @GetMapping("/imagen-articulo")
    public List<ImagenArticulo> getAllImagenesArticulo(){
        return imagenArticuloRepository.findAll();
    }

    @GetMapping("/imagen-articulo/{id}")
    public ImagenArticulo getImagenArticuloById(@PathVariable long id){
        return imagenArticuloRepository.findById(id).orElse(null);
    }

    @PostMapping("/imagen-articulo")
    public ImagenArticulo crearImagenArticulo(@RequestBody ImagenArticulo nuevaImagen) {
        return imagenArticuloRepository.save(nuevaImagen);
    }

    @PatchMapping("/imagen-articulo/{id}")
    public void eliminarImagenArticulo(@PathVariable Long id) {
        ImagenArticulo imagenArticulo = imagenArticuloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ImagenArticulo no encontrado con id: " + id));
        imagenArticulo.setEliminado(true);
        imagenArticuloRepository.save(imagenArticulo);
    }

    @PutMapping("/imagen-articulo/{id}")
    public ImagenArticulo actualizarImagenArticulo(@PathVariable Long id, @RequestBody ImagenArticulo datosActualizados) {
        ImagenArticulo imagenArticulo = imagenArticuloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ImagenArticulo no encontrado con id: " + id));
        imagenArticulo.setUrl(datosActualizados.getUrl());
        // Actualiza otros atributos según sea necesario
        return imagenArticuloRepository.save(imagenArticulo);
    }
}
