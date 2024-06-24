package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Factura;
import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.entities.PedidoDetalle;
import com.example.buensaboruno.repositories.FacturaRepository;
import com.example.buensaboruno.repositories.PedidoRepository;
import com.example.buensaboruno.services.FacturaService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public FacturaServiceImpl(FacturaRepository facturaRepository) {
        super(facturaRepository);
        this.facturaRepository = facturaRepository;
    }

    @Override
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

}
