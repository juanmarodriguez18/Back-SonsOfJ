package com.example.buensaboruno.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@Audited
public class UnidadMedida extends Base{

    private String denominacion;

}
