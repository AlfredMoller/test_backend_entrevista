/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.DeudasServicios;
import facades.deudasServiciosFacade;
import facades.serviciosFacade;
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

    @POST
    @Path("deudaByServicio")
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
            boolean logId = jsonObject.getBoolean("logId", true); // Por defecto, true, lo que indica que se usa el idUsuario

            Integer idServicio = servfacades.obtenerServicioPorNombre(nombreServicio);
            if (idServicio == null) {
                JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("error", "El nombre del servicio proporcionado no existe.")
                    .build();
                return Response.status(Response.Status.NOT_FOUND).entity(jsonResponse).build();
            }

            System.out.println("[ID Servicio]: " + idServicio);
            System.out.println("[ID Usuario - JSON]: " + idUsuario);
            System.out.println("[NOMB SER - JSON]: " + nombreServicio);
            System.out.println("[NIS - JSON]: " + nisOCedula);
            System.out.println("[LOG - JSON]: " + logId);
            
            // Reutiliza la función con logId para controlar la búsqueda
            List<DeudasServicios> deudas = deufacades.listarDeaudasByParams(idUsuario, idServicio, nisOCedula, logId);

            // Construir la respuesta JSON con las deudas encontradas
            JsonObject jsonResponse;
            if (deudas.isEmpty()) {
                jsonResponse = Json.createObjectBuilder()
                    .add("message", "No se encontraron deudas para el usuario y servicio especificados.")
                    .build();
            } else {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                deudas.forEach(deuda -> {
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                        .add("id_deuda", deuda.getIdDeuda())
                        .add("monto_deuda", deuda.getMontoDeudaTotal())
                        .add("fecha_vencimiento", deuda.getFechaVencimiento().toString());

                    arrayBuilder.add(objectBuilder);
                });

                jsonResponse = Json.createObjectBuilder()
                    .add("message", "Deudas encontradas")
                    .add("deudas", arrayBuilder)
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
