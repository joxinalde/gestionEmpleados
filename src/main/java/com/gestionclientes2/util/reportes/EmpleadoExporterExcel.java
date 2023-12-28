package com.gestionclientes2.util.reportes;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestionclientes2.model.Empleado;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//initialized final fields are excluded to AllArgsConstructor
public class EmpleadoExporterExcel {
	private final XSSFWorkbook libro = new XSSFWorkbook();
	private final XSSFSheet hoja = libro.createSheet("Empleados");
	private List<Empleado> listaEmpleados;

	private void escribirCabeceraDeTabla() {
		Row fila = hoja.createRow(0);
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);

		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);

		celda = fila.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);

		celda = fila.createCell(2);
		celda.setCellValue("Apellido");
		celda.setCellStyle(estilo);

		celda = fila.createCell(3);
		celda.setCellValue("Email");
		celda.setCellStyle(estilo);

		celda = fila.createCell(4);
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);

		celda = fila.createCell(5);
		celda.setCellValue("Telefono");
		celda.setCellStyle(estilo);

		celda = fila.createCell(6);
		celda.setCellValue("Genero");
		celda.setCellStyle(estilo);

		celda = fila.createCell(7);
		celda.setCellValue("Salario");
		celda.setCellStyle(estilo);

	}

	private void escribirDatosDeLaTabla() {

		int numeroFilas = 1;
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (Empleado empleado : listaEmpleados) {
			Row fila = hoja.createRow(numeroFilas++);

			Cell celda = fila.createCell(0);
			celda.setCellValue(empleado.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = fila.createCell(1);
			celda.setCellValue(empleado.getNombre());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = fila.createCell(2);
			celda.setCellValue(empleado.getApellido());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);

			celda = fila.createCell(3);
			celda.setCellValue(empleado.getEmail());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);

			celda = fila.createCell(4);
			celda.setCellValue(empleado.getFecha().toString());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);

			celda = fila.createCell(5);
			celda.setCellValue(empleado.getTelefono());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = fila.createCell(6);
			celda.setCellValue(empleado.getSexo());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = fila.createCell(7);
			celda.setCellValue(empleado.getSalario());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);

		}
	}

	public void exportar(HttpServletResponse response) throws IOException {
		escribirCabeceraDeTabla();
		escribirDatosDeLaTabla();
		ServletOutputStream outPutStream = response.getOutputStream();
		libro.write(outPutStream);
		libro.close();
		outPutStream.close();
	}
}
