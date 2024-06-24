package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Factura;
import com.example.buensaboruno.servicesImpl.EmailServiceImpl;
import com.example.buensaboruno.servicesImpl.FacturaServiceImpl;
import com.example.buensaboruno.servicesImpl.PdfServiceImpl;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/facturacion")
public class FacturaController extends BaseControllerImpl<Factura, FacturaServiceImpl> {

    public FacturaController(FacturaServiceImpl service) {
        super(service);
    }

    @Autowired
    FacturaServiceImpl facturaService;
    @Autowired
    PdfServiceImpl pdfService;
    @Autowired
    EmailServiceImpl emailService;

    @PostMapping("/emitir")
    public void emitirFactura(@RequestBody Factura factura, @RequestParam String emailCliente) throws Exception {
        // Persistir la factura
        Factura facturaGuardada = facturaService.save(factura);
        System.out.println("facturaGuardada.getId() = " + facturaGuardada.getId());
        //Factura factura = facturaService.findById(id);

        // Generar el PDF
        byte[] pdfContent = pdfService.generarFacturaPdf(facturaGuardada);

        // Enviar el PDF por email
        emailService.facturaPorEmail(emailCliente, pdfContent);
    }

    @GetMapping("/factura/{idPedido}")
    public ResponseEntity<byte[]> descargarFactura(@PathVariable long idPedido) throws DocumentException, IOException {
        Factura factura = new Factura();
        byte[] pdfContent = pdfService.generarFacturaPdf(factura);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("attachement", "factura.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }

}
