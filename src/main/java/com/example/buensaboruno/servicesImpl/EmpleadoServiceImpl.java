package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.domain.entities.Sucursal;
import com.example.buensaboruno.repositories.EmpleadoRepository;
import com.example.buensaboruno.repositories.SucursalRepository;
import com.example.buensaboruno.services.EmpleadoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl extends BaseServiceImpl<Empleado, Long> implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final SucursalRepository sucursalRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, SucursalRepository sucursalRepository, PasswordEncoder passwordEncoder) {
        super(empleadoRepository);
        this.empleadoRepository = empleadoRepository;
        this.sucursalRepository = sucursalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Empleado update(Long id, Empleado updatedEmpleado) {
        Empleado existingEmpleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        existingEmpleado.setNombre(updatedEmpleado.getNombre());
        existingEmpleado.setApellido(updatedEmpleado.getApellido());
        existingEmpleado.setTelefono(updatedEmpleado.getTelefono());
        existingEmpleado.setEmail(updatedEmpleado.getEmail());
        existingEmpleado.setFechaNacimiento(updatedEmpleado.getFechaNacimiento());
        existingEmpleado.setTipoEmpleado(updatedEmpleado.getTipoEmpleado());


        if (updatedEmpleado.getImagenEmpleado() != null) {
            existingEmpleado.setImagenEmpleado(updatedEmpleado.getImagenEmpleado());
        }

        if (updatedEmpleado.getSucursal() != null) {
            Sucursal sucursal = sucursalRepository.findById(updatedEmpleado.getSucursal().getId())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            existingEmpleado.setSucursal(sucursal);
        }

        return empleadoRepository.save(existingEmpleado);
    }
}

