package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Empresa;
import com.example.buensaboruno.repositories.EmpresaRepository;
import com.example.buensaboruno.services.EmpresaService;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends BaseServiceImpl<Empresa, Long> implements EmpresaService {

    private EmpresaRepository empresaRepository;
    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        super(empresaRepository);
        this.empresaRepository = empresaRepository;
    }
}
