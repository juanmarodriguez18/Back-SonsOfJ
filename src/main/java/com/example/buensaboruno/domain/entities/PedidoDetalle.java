package com.example.buensaboruno.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "pedido_id")
    @JsonBackReference(value = "pedido-pedidodetalle")
    private Pedido pedido;

}
