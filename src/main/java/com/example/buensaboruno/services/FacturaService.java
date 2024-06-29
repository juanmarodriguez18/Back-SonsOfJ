package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Factura;
import com.lowagie.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface FacturaService extends BaseService<Factura, Long>{

    public Factura save(Factura request);
    public Factura getByPedidoId(long id);

}
