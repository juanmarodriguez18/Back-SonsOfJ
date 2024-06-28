package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.dto.LoginDTO;
import com.example.buensaboruno.dto.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String, String> login(LoginDTO login) throws Exception;
    public ResponseDTO register(Empleado empleado) throws Exception;
    Empleado getEmpleadoById(Long id);
    public HashMap<String, String> loginCliente(LoginDTO login) throws Exception;
    public ResponseDTO registerCliente(Cliente cliente) throws Exception;
    Cliente getClienteById(Long id);
}
