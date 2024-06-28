package com.example.buensaboruno.validations;

import com.example.buensaboruno.domain.entities.Cliente;
import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.dto.ResponseDTO;

public class UserValidation {

    public ResponseDTO validate (Empleado empleado){
        ResponseDTO response = new ResponseDTO();

        response.setNumOfErrors(0);

        if (empleado.getEmail() == null ||
                empleado.getEmail().length() < 3 ||
                empleado.getEmail().length() > 50
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo email no puede ser nulo y debe tener entre 3 y 50 caracteres");
        }

        if (empleado.getClave() == null || !empleado.getClave().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos 1 numero, una minúscula y una mayúscula.");
        }

        return response;
    }

    public ResponseDTO validateCliente (Cliente cliente){
        ResponseDTO response = new ResponseDTO();

        response.setNumOfErrors(0);

        if (cliente.getEmail() == null ||
                cliente.getEmail().length() < 3 ||
                cliente.getEmail().length() > 50
        ){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("El campo email no puede ser nulo y debe tener entre 3 y 50 caracteres");
        }

        if (cliente.getClave() == null || !cliente.getClave().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")){
            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos 1 numero, una minúscula y una mayúscula.");
        }

        return response;
    }
}
