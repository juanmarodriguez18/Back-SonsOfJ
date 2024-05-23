package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.repositories.EmpleadoRepository;
import com.example.buensaboruno.services.EmpleadoService;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl extends BaseServiceImpl<Empleado, Long> implements EmpleadoService {

    private EmpleadoRepository empleadoRepository;
    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        super(empleadoRepository);
        this.empleadoRepository = empleadoRepository;
    }
}
