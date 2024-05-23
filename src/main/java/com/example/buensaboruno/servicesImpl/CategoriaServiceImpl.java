package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Categoria;
import com.example.buensaboruno.repositories.CategoriaRepository;
import com.example.buensaboruno.services.CategoriaService;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends BaseServiceImpl<Categoria, Long> implements CategoriaService {

    private CategoriaRepository categoriaRepository;
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
        this.categoriaRepository = categoriaRepository;
    }
}
