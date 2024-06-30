package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.repositories.ClienteRepository;
import com.example.buensaboruno.repositories.PedidoRepository;
import com.example.buensaboruno.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements ClienteService {

    private ClienteRepository clienteRepository;
    private PedidoRepository pedidoRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
        super(clienteRepository);
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findPedidosByClienteId(Long id) {
        return pedidoRepository.findByClienteId(id);
    }
}
