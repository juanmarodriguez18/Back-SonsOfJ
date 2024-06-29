package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Pedido;
import com.example.buensaboruno.services.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ByteArrayInputStream generarReporteRankingComidas(List<Object[]> datos) throws IOException {
        String[] columnas = {"Comida", "Pedidos"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ranking Comidas");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue((String) fila[0]);
                row.createCell(1).setCellValue((Long) fila[1]);
            }

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream generarReporteIngresosDiarios(List<Object[]> datos) throws IOException {
        String[] columnas = {"Día", "Ingresos"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ingresos Diarios");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowIdx++);

                // Convertir java.sql.Date a String
                Date date = (Date) fila[0];
                String formattedDate = dateFormat.format(date);

                row.createCell(0).setCellValue(formattedDate);
                row.createCell(1).setCellValue((Double) fila[1]);
            }

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream generarReporteIngresosMensuales(List<Object[]> datos) throws IOException {
        String[] columnas = {"Año", "Mes", "Ingresos"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ingresos Mensuales");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue((Integer) fila[0]);
                row.createCell(1).setCellValue((Integer) fila[1]);
                row.createCell(2).setCellValue((Double) fila[2]);
            }

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream generarReportePedidosPorCliente(List<Object[]> datos) throws IOException {
        String[] columnas = {"Nombre", "Apellido", "Cantidad de Pedidos"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Pedidos por Cliente");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Object[] fila : datos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue((String) fila[0]);
                row.createCell(1).setCellValue((String) fila[1]);
                row.createCell(2).setCellValue((Long) fila[2]);
            }

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream generarReporteGanancia(Double ganancia) throws IOException {
        String[] columnas = {"Ganancia"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ganancia");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue(ganancia);

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream generarReportePedidos(List<Pedido> pedidos) throws IOException {
        String[] columnas = {"Código", "Total", "Estado", "Tipo Envío", "Fecha"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Pedidos");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Datos
            int rowIdx = 1;
            for (Pedido pedido : pedidos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(pedido.getId());
                row.createCell(1).setCellValue(pedido.getTotal());
                row.createCell(2).setCellValue(pedido.getEstado().name());
                row.createCell(3).setCellValue(pedido.getTipoEnvio().name());
                row.createCell(4).setCellValue(dateFormat.format(Date.valueOf(pedido.getFechaPedido())));
            }

            // Ajustar tamaño de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
