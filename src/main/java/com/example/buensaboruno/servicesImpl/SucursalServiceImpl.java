package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Domicilio;
import com.example.buensaboruno.domain.entities.ImagenSucursal;
import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.repositories.DomicilioRepository;
import com.example.buensaboruno.repositories.ImagenArticuloRepository;
import com.example.buensaboruno.repositories.ImagenSucursalRepository;
import com.example.buensaboruno.repositories.SucursalRepository;
import com.example.buensaboruno.services.SucursalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SucursalServiceImpl extends BaseServiceImpl<Sucursal, Long> implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final ImagenSucursalRepository imagenSucursalRepository;
    private final DomicilioRepository domicilioRepository;

    public SucursalServiceImpl(SucursalRepository sucursalRepository, ImagenSucursalRepository imagenSucursalRepository, DomicilioRepository domicilioRepository) {
        super(sucursalRepository);
        this.sucursalRepository = sucursalRepository;
        this.imagenSucursalRepository = imagenSucursalRepository;
        this.domicilioRepository = domicilioRepository;
    }

    @Override
    public Sucursal findById(Long id) throws Exception {
        return sucursalRepository.findById(id).orElse(null);
    }

    // Método para obtener los pedidos de una sucursal específica
    public Set<Pedido> getPedidosBySucursal(Long sucursalId) throws Exception {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(sucursalId);
        if (sucursalOptional.isPresent()) {
            Sucursal sucursal = sucursalOptional.get();
            return sucursal.getPedidos(); // Retorna la lista de pedidos asociados a la sucursal
        } else {
            throw new Exception("Sucursal no encontrada con ID: " + sucursalId);
        }
    }

    @Override
    public Sucursal save(Sucursal sucursal) {
        // Asegurarse de que las imágenes tengan referencia a la sucursal
        Set<ImagenSucursal> nuevasImagenes = sucursal.getImagenesSucursal().stream()
                .peek(imagen -> imagen.setSucursal(sucursal))
                .collect(Collectors.toSet());
        sucursal.setImagenesSucursal(nuevasImagenes);

        // Obtener el domicilio asociado a la sucursal
        Domicilio domicilio = sucursal.getDomicilio();

        // Asegurarse de que el domicilio no tenga el ID en 0
        if (domicilio != null && domicilio.getId() == 0) {
            domicilio.setId(null);
        }

        // Asociar el domicilio a la sucursal
        sucursal.setDomicilio(domicilio);

        // Guardar el domicilio primero
        domicilioRepository.save(domicilio);

        // Ahora que el domicilio tiene un ID generado, guardar la sucursal
        return sucursalRepository.save(sucursal);
    }

    public List<Sucursal> findByEmpresa(Long empresaId) {
        return sucursalRepository.findByEmpresa(empresaId);
    }


}
