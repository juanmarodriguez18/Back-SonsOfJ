package com.example.buensaboruno.services;

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
public class ExcelService {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
}
