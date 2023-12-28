package com.gestionclientes2.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestionclientes2.model.Empleado;
import com.gestionclientes2.servicio.EmpleadoService;
import com.gestionclientes2.util.paginacion.PageRender;
import com.gestionclientes2.util.reportes.EmpleadoExporterExcel;
import com.gestionclientes2.util.reportes.EmpleadoExporterPDF;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

	@GetMapping("/ver/{id}")
	public String verDetallesDelEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Empleado empleado = empleadoService.findOne(id);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("empleado", empleado);
		model.put("titulo", "Detalles del empleado ID N. " + empleado.getId());
		return "ver";

	}

	@GetMapping({ "/", "/listar", "" })
	public ModelAndView listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page) {
		Map<String, Object> model = new HashMap<String, Object>();
		Pageable pageRequest = PageRequest.of(page, 5, Sort.by("salario").descending());
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);
		model.put("titulo", "Listado de empleados");
		model.put("empleados", empleados);
		model.put("page", pageRender);
		return new ModelAndView("listar", model);
	}

	@GetMapping("/form")
	public String mostrarFormularioDeRegistroEmpleado(Map<String, Object> model) {
		Empleado empleado = new Empleado();
		model.put("empleado", empleado);
		model.put("titulo", "Registro de Empleados");
		return "form";
	}

	@PostMapping("/form")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registro de empleado");
			return "form";
		}

		String mensaje = (empleado.getId() != null) ? "El empleado ha sido editado con exito"
				: "Empleado registrado con exito";
		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}

	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Empleado empleado = null;
		if (id > 0) {
			empleado = empleadoService.findOne(id);
			if (empleado == null) {
				flash.addFlashAttribute("error", "El ID del empleado no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del empleado no puede ser 0");
			return "redirect:/listar";
		}

		model.put("empleado", empleado);
		model.put("titulo", "Edicion de empleado");
		return "form";
	}
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		if (id > 0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success", "Empleado eliminado con exito");
		}
			return "redirect:/listar";
		}
	
	@GetMapping("/exportarPDF")
	public void exportarListadoEmpleadosPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM--dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		List<Empleado> empleados = empleadoService.findAll();
		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListadoEmpleadosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octec-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM--dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xslx";
		
		response.setHeader(cabecera, valor);
		List<Empleado> empleados = empleadoService.findAll();
		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportar(response);
	}
}
