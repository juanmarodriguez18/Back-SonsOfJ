package com.example.buensaboruno.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class ArticuloInsumo extends Articulo{

    private Double precioCompra;
    private Double stockActual;
    private Integer stockMinimo;
    private Boolean esParaElaborar;

}
