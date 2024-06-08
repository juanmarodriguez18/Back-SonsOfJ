package com.example.buensaboruno.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@Audited
public class Sucursal extends  Base{

    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    protected Set<ImagenSucursal> imagenesSucursal = new HashSet<>();

    @ManyToOne
    //@JsonBackReference
    @JsonIgnore
    private Empresa empresa;

    @OneToOne
    private Domicilio domicilio;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "sucursal_promocion",
            joinColumns = @JoinColumn(name = "promocion_id"),
            inverseJoinColumns = @JoinColumn(name = "sucursal_id"))
    @Builder.Default
    private Set<Promocion> promociones = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinTable(name = "sucursal_categoria",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @Builder.Default
    private Set<Categoria> categorias = new HashSet<>();

    @OneToMany(mappedBy = "sucursal",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private Set<Empleado> empleados = new HashSet<>();

    @OneToMany(mappedBy = "sucursal",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference(value = "sucursal-pedido")
    private Set<Pedido> pedidos = new HashSet<>();


}
