/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.DeudasServicios;
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


/**
 *
 * @author pc
 */

@Path("servicios")
public class ServiciosController {

    @EJB
    private serviciosFacade servfacades;

    @EJB
    private deudasServiciosFacade deufacades;
    
    @EJB
    private cuentasfacade cufacades;

    
    @POST
    @Path("consultarDeudas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarServiciosDeudas(JsonObject jsonObject) {
        try {
            Integer idUsuario = null;
            if (jsonObject.containsKey("id_usuario") && !jsonObject.isNull("id_usuario")) {
                idUsuario = jsonObject.getInt("id_usuario");
            }

            String nombreServicio = jsonObject.getString("nombre_servicio", "N/A");
            String nisOCedula = jsonObject.containsKey("nis_ocedula") && !jsonObject.isNull("nis_ocedula")
                                ? jsonObject.getString("nis_ocedula") : null;
            boolean logId = jsonObject.getBoolean("logId", true);
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
    
    
    @POST
    @Path("procesarPago")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarSaldoParaPago(JsonObject jsonObject) {
        try {
            Integer idUsuario = jsonObject.getInt("id_usuario");
            String nombreServicio = jsonObject.getString("nombre_servicio", "N/A");
            String fechaDeudaStr = jsonObject.getString("fecha_deuda", null);

            BigDecimal montoDeuda = new BigDecimal(jsonObject.getString("monto_deuda"));
            
            // Convertir la fecha de deuda de String a Date
            Date fechaDeuda = null;
            if (fechaDeudaStr != null && !fechaDeudaStr.isEmpty()) {
                fechaDeuda = java.sql.Date.valueOf(fechaDeudaStr);
            }

            Integer idServicio = servfacades.obtenerServicioPorNombre(nombreServicio);
            if (idServicio == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("error", "El nombre del servicio proporcionado no existe.").build()
                ).build();
            }
            
            // Verificar si la deuda existe para el usuario, servicio y fecha especificados
            DeudasServicios deuda = deufacades.buscarDeudaPorFechaYServicio(idUsuario, idServicio, fechaDeuda);
            if (deuda == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("error", "No se encontró una deuda para el servicio y la fecha especificados.").build()
                ).build();
            }

            // Verificar si el usuario tiene saldo suficiente para pagar la deuda
            boolean saldoSuficiente = cufacades.tieneSaldoSuficiente(idUsuario, montoDeuda);

            JsonObject jsonResponse;
            if (saldoSuficiente) {
                jsonResponse = Json.createObjectBuilder()
                    .add("message", "El usuario tiene suficiente saldo para pagar la deuda.")
                    .build();
            } else {
                jsonResponse = Json.createObjectBuilder()
                    .add("message", "El usuario no tiene suficiente saldo para pagar la deuda.")
                    .build();
            }

            return Response.ok(jsonResponse).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }

}
