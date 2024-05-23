package com.example.buensaboruno.domain.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ImagenPromocion extends Base {

    private String url;
}
