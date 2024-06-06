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
public class ImagenEmpresa extends Base {

    private String url;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name= "empresa_id")
    @JsonBackReference(value = "imagen_empresa")
    private Empresa empresa;

}
