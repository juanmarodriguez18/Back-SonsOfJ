package com.example.buensaboruno.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ImagenArticulo extends Base{

    @Column(columnDefinition = "TEXT")
    private String url;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name= "articulo_id")
    @JsonBackReference(value = "imagen_articulo")
    private Articulo articulo;
}
