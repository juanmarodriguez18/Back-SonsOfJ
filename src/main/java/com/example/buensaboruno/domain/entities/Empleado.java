package com.example.buensaboruno.domain.entities;

import com.example.buensaboruno.domain.enums.Rol;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Empleado extends Base {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private Rol tipoEmpleado;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @OneToOne
    @NotAudited
    private ImagenEmpleado imagenEmpleado;

    @OneToOne
    @NotAudited
    private UsuarioEmpleado usuarioEmpleado;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.REFRESH, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    @JsonManagedReference(value = "empleado-pedido")
    private Set<Pedido> pedidos = new HashSet<>();

}
