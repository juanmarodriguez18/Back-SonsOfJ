package com.example.buensaboruno.services;

import com.example.buensaboruno.domain.entities.Factura;
import com.lowagie.text.DocumentException;

import java.io.IOException;

public interface PdfService {

    public byte[] generarFacturaPdf(Factura factura) throws IOException, DocumentException;

}
