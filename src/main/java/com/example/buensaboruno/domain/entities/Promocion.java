package com.example.buensaboruno.domain.entities;

import com.example.buensaboruno.domain.enums.TipoPromocion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
public class Promocion extends Base{
    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    protected Set<ImagenPromocion> imagenesPromocion = new HashSet<>();

    @ManyToMany (mappedBy = "promociones")
    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();

    @OneToMany
    @JoinColumn(name="promocion_id")
    @Builder.Default
    private Set<PromocionDetalle> promocionDetalles= new HashSet<>();

}
