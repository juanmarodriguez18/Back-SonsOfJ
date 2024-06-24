package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Factura;
import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.domain.entities.PedidoDetalle;
import com.example.buensaboruno.repositories.FacturaRepository;
import com.example.buensaboruno.services.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xddf.usermodel.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Service
public class PdfServiceImpl implements PdfService  {

    @Autowired
    FacturaRepository facturaRepository;
    protected static Font titulo = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    protected static Font texto = FontFactory.getFont(FontFactory.TIMES, 11);
    protected static Font textoBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);

    @Override
    public byte[] generarFacturaPdf(Factura factura) throws IOException, DocumentException {
        System.out.println("factura: " + factura.getId());
        //Optional<Factura> optionalFactura = facturaRepository.findById(factura.getId());

        if(factura.getPedido() == null || factura.getId() == 0) {
            throw new RuntimeException("No se encuentra la factura.");
        }

        //Crear documento
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.A4);
        Pedido pedido = factura.getPedido();
        PdfWriter.getInstance(documento, baos);

        //Abrir documento
        documento.open();

        configurarEncabezado(documento, pedido);
        configurarDatosCliente(documento, pedido);
        configurarDetalle(documento, pedido);

        //Cerrar documento
        documento.close();
        System.out.println("pedido = " + pedido.getId());
        return baos.toByteArray();
    }

    //---- Encabezado ----//
    private void configurarEncabezado(Document doc, Pedido pedido) throws DocumentException, MalformedURLException, IOException {
        //Inicializar datos
        String logoString = pedido.getSucursal().getEmpresa().getImagenesEmpresa().iterator().next().getUrl();
        Image logo = Image.getInstance(logoString);
        logo.scalePercent(28f);
        String nombreEmpresa = pedido.getSucursal().getEmpresa().getNombre();
        String nombreSucursal = pedido.getSucursal().getNombre();
        String cuit = pedido.getSucursal().getEmpresa().getCuil().toString();
        String direccion = pedido.getSucursal().getDomicilio().getCalle() + " " + pedido.getSucursal().getDomicilio().getNumero() + " - " + pedido.getSucursal().getDomicilio().getLocalidad().getNombre();

        //Crear tablas
        PdfPTable tablaEncabezado = new PdfPTable(3);
        tablaEncabezado.setWidthPercentage(100f);
        float[] anchoColumnas = {1f, 2f, 2f};
        tablaEncabezado.setWidths(anchoColumnas);

        PdfPTable tablaEncabezadoDelMedio = new PdfPTable(1);
        tablaEncabezadoDelMedio.setWidthPercentage(100f);

        PdfPTable tablaEncabezadoDerecha = new PdfPTable(1);
        tablaEncabezadoDerecha.setWidthPercentage(100f);

        //Crear celdas
        PdfPCell empresa = new PdfPCell(new Phrase(nombreEmpresa, titulo));
        empresa.setBorder(Rectangle.NO_BORDER);
        empresa.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell sucursal = new PdfPCell(new Phrase("Sucursal " + nombreSucursal));
        sucursal.setBorder(Rectangle.NO_BORDER);
        PdfPCell nroCuit = new PdfPCell(new Phrase("CUIT: " + cuit));
        nroCuit.setBorder(Rectangle.NO_BORDER);
        PdfPCell domicilio = new PdfPCell(new Phrase(direccion));
        domicilio.setBorder(Rectangle.NO_BORDER);
        PdfPCell fecha = new PdfPCell(new Phrase("Fecha: " + pedido.getFechaPedido()));

        tablaEncabezadoDelMedio.addCell(empresa);
        tablaEncabezadoDelMedio.addCell(sucursal);
        tablaEncabezadoDelMedio.addCell(nroCuit);
        tablaEncabezadoDelMedio.addCell(domicilio);

        PdfPCell celdaIzquierda = new PdfPCell(logo);
        celdaIzquierda.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM);
        PdfPCell celdaDelMedio = new PdfPCell(tablaEncabezadoDelMedio);
        celdaDelMedio.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        fecha.setBorder(Rectangle.RIGHT | Rectangle.TOP | Rectangle.BOTTOM);

        tablaEncabezado.addCell(celdaIzquierda);
        tablaEncabezado.addCell(celdaDelMedio);
        tablaEncabezado.addCell(fecha);

        doc.add(tablaEncabezado);
        doc.add(new Paragraph(" "));
    }

    //---- Datos cliente ----//
    private void configurarDatosCliente(Document doc, Pedido pedido) throws DocumentException, MalformedURLException, IOException {
        //Inicializar datos
        String nombreCliente = "Nombre: " + pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido();
        String direccion = "Dirección: " + pedido.getDomicilio().getCalle() + " " + pedido.getDomicilio().getNumero() + (!pedido.getDomicilio().getPiso().toString().isEmpty() ? " - piso " + pedido.getDomicilio().getPiso() + " - dpto. " + pedido.getDomicilio().getNroDpto() : "");

        //Crear tabla
        PdfPTable tablaCliente = new PdfPTable(1);
        tablaCliente.setWidthPercentage(100f);
        PdfPCell titulo = new PdfPCell(new Phrase("Cliente"));
        titulo.setBorder(Rectangle.BOX);
        PdfPCell nombre = new PdfPCell(new Paragraph(nombreCliente));
        nombre.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        PdfPCell domicilio = new PdfPCell(new Paragraph(direccion));
        domicilio.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);

        tablaCliente.addCell(titulo);
        tablaCliente.addCell(nombre);
        tablaCliente.addCell(domicilio);

        doc.add(tablaCliente);
        doc.add(new Paragraph(" "));
    }

    //---- Detalle ----//
    private void configurarDetalle(Document doc, Pedido pedido) throws DocumentException, MalformedURLException, IOException {
        //Inicializar datos
        double total = 0;

        //Crear tabla
        PdfPTable tablaDetalle = new PdfPTable(3);
        tablaDetalle.setWidthPercentage(100f);

        //Crear celdas de encabezado
        PdfPCell tituloCantidad = new PdfPCell(new Phrase("Cantidad", textoBold));
        tituloCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell tituloDescripcion = new PdfPCell(new Phrase("Descripción", textoBold));
        tituloDescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell tituloPrecio = new PdfPCell(new Phrase("Subtotal", textoBold));
        tituloPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);

        //Crear celdas con los datos
        tablaDetalle.addCell(tituloCantidad);
        tablaDetalle.addCell(tituloDescripcion);
        tablaDetalle.addCell(tituloPrecio);

        for(PedidoDetalle detalle : pedido.getPedidoDetalles()) {
            PdfPCell cantidadCell = new PdfPCell(new Phrase(detalle.getCantidad().toString(), texto));
            cantidadCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaDetalle.addCell(cantidadCell);
            PdfPCell descripcionCell = new PdfPCell(new Phrase(detalle.getArticulo().getDenominacion(), texto));
            tablaDetalle.addCell(descripcionCell);
            PdfPCell subtotCell = new PdfPCell(new Phrase(String.valueOf(detalle.getSubTotal()), texto));
            subtotCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaDetalle.addCell(subtotCell);
            total += detalle.getSubTotal();
        }

        tablaDetalle.addCell(new Phrase(" "));
        PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL", textoBold));
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaDetalle.addCell(totalCell);
        PdfPCell totCell = new PdfPCell(new Phrase(String.valueOf(total), texto));
        totCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaDetalle.addCell(totCell);

        doc.add(tablaDetalle);
    }

}
