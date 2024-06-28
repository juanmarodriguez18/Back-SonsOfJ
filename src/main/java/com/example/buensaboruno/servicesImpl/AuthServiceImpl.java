package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.dto.LoginDTO;
import com.example.buensaboruno.dto.ResponseDTO;
import com.example.buensaboruno.repositories.ClienteRepository;
import com.example.buensaboruno.repositories.EmpleadoRepository;
import com.example.buensaboruno.services.IAuthService;
import com.example.buensaboruno.services.IJWTUtilityService;
import com.example.buensaboruno.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;

    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        HashMap<String, String> jwt = new HashMap<>();
        try {
            Optional<Empleado> optionalEmpleado = empleadoRepository.findByEmail(login.getEmail());

            if (optionalEmpleado.isPresent()) {
                Empleado empleado = optionalEmpleado.get();
                if (verifyPassword(login.getClave(), empleado.getClave())) {
                    jwt.put("jwt", jwtUtilityService.generateJWT(empleado.getId()));
                } else {
                    jwt.put("error", "Credenciales incorrectas");
                }
            } else {
                jwt.put("error", "Empleado no encontrado");
            }

            return jwt;
        } catch (Exception e) {
            jwt.put("error", "Error durante el login: " + e.getMessage());
            return jwt;
        }
    }

    @Override
    public ResponseDTO register(Empleado empleado) throws Exception {
        try {
            ResponseDTO response = userValidation.validate(empleado);

            if (response.getNumOfErrors() > 0) {
                return response;
            }

            Optional<Empleado> existingEmpleado = empleadoRepository.findByEmail(empleado.getEmail());

            if (existingEmpleado.isPresent()) {
                response.setNumOfErrors(1);
                response.setMessage("Empleado ya registrado");
                return response;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            empleado.setClave(encoder.encode(empleado.getClave()));
            empleadoRepository.save(empleado);
            response.setMessage("Empleado registrado exitosamente");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }


    public HashMap<String, String> loginCliente(LoginDTO login) throws Exception {
        HashMap<String, String> jwt = new HashMap<>();
        try {
            Optional<Cliente> optionalCliente = clienteRepository.findByEmail(login.getEmail());

            if (optionalCliente.isPresent()) {
                Cliente cliente = optionalCliente.get();
                if (verifyPassword(login.getClave(), cliente.getClave())) {
                    jwt.put("jwt", jwtUtilityService.generateJWT(cliente.getId()));
                } else {
                    jwt.put("error", "Credenciales incorrectas");
                }
            } else {
                jwt.put("error", "Cliente no encontrado");
            }

            return jwt;
        } catch (Exception e) {
            jwt.put("error", "Error durante el login: " + e.getMessage());
            return jwt;
        }
    }


    public ResponseDTO registerCliente(Cliente cliente) throws Exception {
        try {
            ResponseDTO response = userValidation.validateCliente(cliente);

            if (response.getNumOfErrors() > 0) {
                return response;
            }

            Optional<Cliente> existingCliente = clienteRepository.findByEmail(cliente.getEmail());

            if (existingCliente.isPresent()) {
                response.setNumOfErrors(1);
                response.setMessage("Cliente ya registrado");
                return response;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            cliente.setClave(encoder.encode(cliente.getClave()));
            clienteRepository.save(cliente);
            response.setMessage("Cliente registrado exitosamente");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
