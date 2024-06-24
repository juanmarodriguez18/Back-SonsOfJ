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
public class ImagenPromocion extends Base {

    private String url;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name= "promocion_id")
    @JsonBackReference(value = "imagen_promocion")
    private Promocion promocion;
}
