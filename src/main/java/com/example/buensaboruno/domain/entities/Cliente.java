package com.example.buensaboruno.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.NotAudited;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
//@Audited
public class Cliente extends Base {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String clave;
    private LocalDate fechaNacimiento;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "cliente_domicilio",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "domicilio_id"))
    @Builder.Default
    //@JsonManagedReference
    private Set<Domicilio> domicilios = new HashSet<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @NotAudited
    private ImagenCliente imagenCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private Set<Pedido> pedidos = new HashSet<>();

    /*
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();
    */
    /*@OneToOne(cascade = CascadeType.MERGE)
    @NotAudited
    private UsuarioCliente usuarioCliente;*/

}