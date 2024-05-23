package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Articulo;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends BaseRepository<Articulo, Long> {
}
