package controller;

import entities.Pagos;
import facades.pagosFacade;
import facades.serviciosFacade;
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
@Path("pagos")
public class pagosController {

    @EJB
    private serviciosFacade servfacades;

    @EJB
    private pagosFacade pagfacade;

    @POST
    @Path("listarPagosPorFecha")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagosPorFecha(JsonObject jsonObject) {
        try {
            String fechaInicioStr = jsonObject.getString("fecha_inicio");
            String fechaFinStr = jsonObject.getString("fecha_fin");
            Integer idUsuario = null;
            if (jsonObject.containsKey("id_usuario") && !jsonObject.isNull("id_usuario")) {
                idUsuario = jsonObject.getInt("id_usuario");
            }
            String nisOCedula = jsonObject.containsKey("nis_ocedula") && !jsonObject.isNull("nis_ocedula")
                    ? jsonObject.getString("nis_ocedula") : null;
            boolean logId = jsonObject.getBoolean("logId", true); // Si true, usar idUsuario; si false, usar nisOCedula
            int page = jsonObject.getInt("page", 1);  // Número de página (default 1)
            int size = jsonObject.getInt("size", 10); // Tamaño de página (default 10)

            Date fechaInicio = java.sql.Date.valueOf(fechaInicioStr);
            Date fechaFin = java.sql.Date.valueOf(fechaFinStr);

            // Lógica para obtener los pagos
            List<Pagos> pagos = pagfacade.listarPagosPorFecha(idUsuario, nisOCedula, fechaInicio, fechaFin, logId, page, size);
            int totalPagos = pagfacade.contarPagos(idUsuario, nisOCedula, fechaInicio, fechaFin, logId); // Método adicional para contar todos los pagos

            // Construir la respuesta JSON con los pagos encontrados
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            if (pagos.isEmpty()) {
                responseBuilder.add("message", "No se encontraron pagos en el rango de fechas especificado.");
            } else {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                pagos.forEach(pago -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                            .add("id_pago", pago.getIdPago())
                            .add("monto_pago", pago.getMontoPago().toString()) // Convertir BigDecimal a String
                            .add("fecha_pago", pago.getFechaPago().toString()));
                            //.add("nombre_servicio", pago.getNombreServicio())
                });

                responseBuilder.add("message", "Pagos encontrados")
                        .add("pagos", arrayBuilder)
                        .add("total_pagos", totalPagos)
                        .add("pagina_actual", page)
                        .add("tamano_pagina", size)
                        .add("total_paginas", (int) Math.ceil((double) totalPagos / size));
            }

            return Response.ok(responseBuilder.build()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud: " + e.getMessage())
                    .build();
        }
    }

}
