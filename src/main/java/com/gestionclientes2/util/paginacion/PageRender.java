package com.gestionclientes2.util.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PageRender<T> {
	private String url;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Page<T> page;
	private int totalPaginas;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private int indicePaginas;
	private int paginaActual;
	private List<PageItem> paginas;

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		indicePaginas = 5;
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;
		int desde, hasta;
		if (totalPaginas <= indicePaginas) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= indicePaginas / 2) {
				desde = 1;
				hasta = indicePaginas;
			} else if (paginaActual >= totalPaginas - indicePaginas / 2) {
				desde = totalPaginas - indicePaginas + 1;
				hasta = indicePaginas;
			} else {
				desde = paginaActual - indicePaginas / 2;
				hasta = indicePaginas;
			}
		}
		
		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevius() {
		return page.hasPrevious();
	}
}


// min 42 https://youtu.be/H8lbgOEdZ9E?t=2555

/*

Este código parece ser parte de un algoritmo utilizado para generar una lista de ítems de página (o "PageItems") para la paginación en un sistema. La paginación se utiliza comúnmente en aplicaciones web para dividir grandes conjuntos de datos en páginas más pequeñas, facilitando la navegación del usuario.

Voy a explicar el código paso a paso:

Definición de Variables:

indicePaginas = 5: Este valor representa el número máximo de ítems de página que se mostrarán en la barra de paginación.
totalPaginas = page.getTotalPages(): Obtiene el número total de páginas en el conjunto de datos.
paginaActual = page.getNumber() + 1: Obtiene el número de la página actual y se suma 1, ya que es común que las páginas se muestren empezando desde 1 en lugar de 0.
Cálculo de Rango de Páginas a Mostrar:

Se verifica si el número total de páginas (totalPaginas) es menor o igual que el número máximo de ítems de página a mostrar (indicePaginas).
Si es así, se muestran todas las páginas desde 1 hasta el total de páginas.
En caso contrario, se calcula un rango de páginas centrado alrededor de la página actual. Si la página actual está cerca del principio o del final, se muestran las primeras o las últimas indicePaginas páginas, respectivamente. Si la página actual está en el medio, se muestra un rango centrado alrededor de ella.
Generación de PageItems:

Se utiliza un bucle for para generar objetos PageItem que representan cada página en el rango calculado.
Cada PageItem tiene un número de página y un indicador booleano que indica si esa página es la actual (paginaActual == desde + i).
En resumen, este código genera una lista de ítems de página para mostrar en la interfaz de usuario, centrándose en la página actual y asegurándose de que no se muestren demasiadas páginas a la vez. El propósito final es proporcionar una barra de paginación fácil de entender y usar para el usuario.
*/