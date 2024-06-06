package com.example.buensaboruno.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class ImagenSucursal extends Base {

    private String url;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name= "sucursal_id")
    @JsonBackReference(value = "imagen_sucursal")
    private Sucursal sucursal;

}
