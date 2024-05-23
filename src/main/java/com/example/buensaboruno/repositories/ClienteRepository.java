package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.Base;
import com.example.buensaboruno.domain.entities.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente,Long> {
}
