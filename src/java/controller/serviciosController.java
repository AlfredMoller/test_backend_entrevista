/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.DeudasServicios;
import entities.Usuario;
import facades.cuentasfacade;
import facades.deudasServiciosFacade;
import facades.serviciosFacade;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;


/**
 *
 * @author pc
 */

@Path("servicios")
public class serviciosController {

    @EJB
    private serviciosFacade servfacades;

    @EJB
    private deudasServiciosFacade deufacades;

    
    @POST
    @Path("consultarDeudas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarServiciosDeudas(JsonObject jsonObject, @Context HttpServletRequest request) {
        try {
             // Obtener el ID del usuario desde la sesión
            HttpSession session = request.getSession(false);
            Integer idUsuario = null;
            boolean logId = false; // Por defecto es false

            if (session != null && session.getAttribute("usuario") != null) {
                Usuario usuarioEnSesion = (Usuario) session.getAttribute("usuario");
                idUsuario = usuarioEnSesion.getIdUsuario();
                logId = true; // Si hay sesión activa, establecer logId como true
            }

            // Validar que nisOCedula sea obligatorio si no hay sesión
            String nisOCedula = null;
            if (idUsuario == null) { // No hay sesión activa
                nisOCedula = jsonObject.containsKey("nis_ocedula") && !jsonObject.isNull("nis_ocedula")
                        ? jsonObject.getString("nis_ocedula") : null;

                if (nisOCedula == null || nisOCedula.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Json.createObjectBuilder()
                                .add("error", "El campo nis_ocedula es obligatorio si no está logueado.")
                                .build())
                            .build();
                }
            } else {
                nisOCedula = jsonObject.containsKey("nis_ocedula") && !jsonObject.isNull("nis_ocedula")
                        ? jsonObject.getString("nis_ocedula") : null;
            }

            String nombreServicio = jsonObject.getString("nombre_servicio", "N/A");
            int page = jsonObject.getInt("page", 1);  // Número de página (default 1)
            int size = jsonObject.getInt("size", 10); // Tamaño de página (default 10)

            Integer idServicio = servfacades.obtenerServicioPorNombre(nombreServicio);
            if (idServicio == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("error", "El nombre del servicio proporcionado no existe.").build()
                ).build();
            }

            // Reutiliza la función con logId para controlar la búsqueda
            List<DeudasServicios> deudas = deufacades.listarDeaudasByParams(idUsuario, idServicio, nisOCedula, logId, page, size);
            int totalDeudas = deufacades.contarDeudas(idUsuario, idServicio, nisOCedula, logId); // Método adicional para contar todas las deudas

            // Construir la respuesta JSON con las deudas encontradas
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            if (deudas.isEmpty()) {
                responseBuilder.add("message", "No se encontraron deudas para el usuario y servicio especificados.");
            } else {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                deudas.forEach(deuda -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                        .add("id_deuda", deuda.getIdDeuda())
                        .add("monto_deuda", deuda.getMontoDeudaTotal())
                        .add("fecha_vencimiento", deuda.getFechaVencimiento().toString())
                    );
                });

                responseBuilder.add("message", "Deudas encontradas")
                               .add("deudas", arrayBuilder)
                               .add("total_deudas", totalDeudas)
                               .add("pagina_actual", page)
                               .add("tamano_pagina", size)
                               .add("total_paginas", (int) Math.ceil((double) totalDeudas / size));
            }

            return Response.ok(responseBuilder.build()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }
}
