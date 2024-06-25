package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Empleado;
import com.example.buensaboruno.dto.ResponseDTO;
import com.example.buensaboruno.dto.LoginDTO;
import com.example.buensaboruno.services.IAuthService;
import com.example.buensaboruno.services.IJWTUtilityService;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

    private final IAuthService authService;
    private final IJWTUtilityService jwtUtilityService;

    @Autowired
    public AuthControllers(IAuthService authService, IJWTUtilityService jwtUtilityService) {
        this.authService = authService;
        this.jwtUtilityService = jwtUtilityService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody Empleado empleado) {
        try {
            ResponseDTO response = authService.register(empleado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO errorResponse = new ResponseDTO();
            errorResponse.setMessage("Error during registration: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) {
        try {
            HashMap<String, String> loginResponse = authService.login(loginRequest);
            if (loginResponse != null && loginResponse.containsKey("jwt")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(loginResponse != null ? loginResponse : new HashMap<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error during login: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/currentEmpleado")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            JWTClaimsSet claimsSet = jwtUtilityService.parseJWT(token);
            Long empleadoId = Long.parseLong(claimsSet.getSubject());

            Empleado empleado = authService.getEmpleadoById(empleadoId);
            if (empleado == null) {
                return ResponseEntity.status(404).body("Empleado no encontrado");
            }

            return ResponseEntity.ok(empleado);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener el empleado: " + e.getMessage());
        }
    }
}
