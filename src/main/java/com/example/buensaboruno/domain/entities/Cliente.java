package com.example.buensaboruno.domain.entities;

import com.example.buensaboruno.domain.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    private LocalDate fechaNacimiento;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "cliente_domicilio",
            joinColumns = @JoinColumn(name = "domicilio_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id"))
    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();

    @OneToOne
    @NotAudited
    private ImagenCliente imagenCliente;

    @OneToOne
    @NotAudited
    private UsuarioCliente usuarioCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();

    /*
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();
    */

}