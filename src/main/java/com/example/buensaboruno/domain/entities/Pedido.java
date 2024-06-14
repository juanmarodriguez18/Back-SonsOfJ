package com.example.buensaboruno.domain.entities;

import com.example.buensaboruno.domain.enums.Estado;
import com.example.buensaboruno.domain.enums.FormaPago;
import com.example.buensaboruno.domain.enums.TipoEnvio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@Audited
public class Pedido extends Base{
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime horaEstimadaFinalizacion;
    private Double total;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaPedido;

    @ManyToOne
    @JsonBackReference(value = "sucursal-pedido")
    private Sucursal sucursal;

    @ManyToOne
    private Domicilio domicilio;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    @JsonBackReference(value = "empleado-pedido")
    private Empleado empleado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    @Builder.Default
    @NotAudited
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}
