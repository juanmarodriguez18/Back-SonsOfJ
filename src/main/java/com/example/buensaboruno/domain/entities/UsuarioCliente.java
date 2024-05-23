package com.example.buensaboruno.domain.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UsuarioCliente extends Base {

    private String auth0Id;
    private String username;

}
