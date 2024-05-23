package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.repositories.ClienteRepository;
import com.example.buensaboruno.services.ClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements ClienteService {

    private ClienteRepository clienteRepository;
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        super(clienteRepository);
        this.clienteRepository = clienteRepository;
    }
}
