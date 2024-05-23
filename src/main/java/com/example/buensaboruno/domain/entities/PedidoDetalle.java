package com.example.buensaboruno.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@Audited
public class PedidoDetalle extends Base{

    private Integer cantidad;
    private Double subTotal;

    @ManyToOne
    private Articulo articulo;

}
