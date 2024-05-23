package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.UsuarioCliente;
import com.example.buensaboruno.repositories.UsuarioClienteRepository;
import com.example.buensaboruno.services.UsuarioClienteService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioClienteServiceImpl extends BaseServiceImpl<UsuarioCliente, Long> implements UsuarioClienteService {

    private UsuarioClienteRepository usuarioClienteRepository;
    public UsuarioClienteServiceImpl(UsuarioClienteRepository usuarioClienteRepository) {
        super(usuarioClienteRepository);
        this.usuarioClienteRepository = usuarioClienteRepository;
    }
}
