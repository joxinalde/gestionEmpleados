## Observaciones Empleado Controller

### SessionStatus .setComplete()
Cuando se llama a SessionStatus.setComplete(), indica que la lógica de manejo de la solicitud ha sido completada y la sesión debería considerarse completa para el flujo de trabajo actual. Esto significa que cualquier información específica de la sesión asociada con el formulario o el flujo de trabajo se limpia, y si el usuario vuelve a la página y envía el formulario nuevamente, se iniciará un nuevo flujo de trabajo.

En el contexto de un formulario para agregar un nuevo empleado, por ejemplo, al llamar a status.setComplete(), estás indicando que el proceso de agregar un empleado se ha completado y la sesión asociada con ese proceso debería ser considerada como completa. Si el usuario vuelve a la página y envía el formulario nuevamente, se iniciará un nuevo flujo de trabajo para agregar un nuevo empleado, sin afectar al empleado que se agregó previamente.

Es especialmente útil cuando trabajas con flujos de trabajo que involucran formularios y quieres asegurarte de que cada envío del formulario se considere como una operación independiente, sin interferencia de datos de sesiones anteriores.


### RedirectAtributtes . addFlashAtributte()

RedirectAttributes es una interfaz que se utiliza para pasar atributos entre redireccionamientos. Cuando se realiza una redirección en una aplicación Spring, a menudo es necesario pasar información desde el controlador que maneja la solicitud original hasta el controlador que manejará la siguiente solicitud después de la redirección.

El uso típico de RedirectAttributes es agregar atributos que se deben incluir en la URL de redirección o que deben estar disponibles en el modelo del controlador de destino después de la redirección. Estos atributos son generalmente mensajes de éxito, errores o cualquier otro tipo de información que se deba mostrar al usuario después de realizar una acción.

addFlashAttribute se utiliza para agregar un atributo que estará disponible en el siguiente request, pero solo estará disponible durante el ciclo de vida de una redirección. Es especialmente útil para pasar mensajes entre controladores sin tener que usar parámetros de URL directamente.


## Extension Thymeleaf
juhahinkula.thymeleaf

| Trigger  | Content                        |
|----------|--------------------------------|
| thtxt    | th:text="${}"                  |
| thfld    | th:field="*{}"                  |
| theach   | "th:each=" : ${}"               |
| theachli | Thymeleaf each with list        |
| thobj    | th:object="${}"                 |
| thref    | th:href="@{}"                   |
| thform   | Thymeleaf form                  |
| thif     | th:if="${}"                     |
| thswitch | th:switch="${}"                 |
| thcase   | th:case=""                      |
| thval    | th:value=""                     |
| thins    | th:insert=""                    |
| threp    | th:replace=""                   |
| thfr     | th:fragment                     |
| thwith   | th:with=""                      |
| thstyle  | th:style=""                     |
| thtmpl   | HTML thymleaf template          |


###Links
[Paginacion](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
[MostrarMensajesDeErroresTHYMELEAF](https://www.baeldung.com/spring-thymeleaf-error-messages)
