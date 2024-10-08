package controller;

import entities.DeudasServicios;
import entities.Pagos;
import entities.Servicios;
import entities.Usuario;
import facades.cuentasfacade;
import facades.deudasServiciosFacade;
import facades.pagosFacade;
import facades.serviciosFacade;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import models.Usuarios;


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
    
    @EJB
    private cuentasfacade cufacades;
    
    @EJB
    private deudasServiciosFacade deufacades;
    
    
    @POST
    @Path("listarPagosPorFecha")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagosPorFecha(JsonObject jsonObject, @Context HttpServletRequest request) {
        try {
            // Extraer las fechas del JsonObject
            String fechaInicioStr = jsonObject.getString("fecha_inicio");
            String fechaFinStr = jsonObject.getString("fecha_fin");

            // Definir el formato de fecha esperado
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Para asegurarse de que las fechas inválidas lancen una excepción

            // Intentar parsear las fechas
            Date fechaInicio;
            Date fechaFin;
            try {
                fechaInicio = dateFormat.parse(fechaInicioStr);
                fechaFin = dateFormat.parse(fechaFinStr);
            } catch (ParseException e) {
                // Si las fechas no cumplen con el formato, devolver un error
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Json.createObjectBuilder()
                            .add("error", "Las fechas deben estar en el formato 'yyyy-MM-dd'.")
                            .build())
                        .build();
            }

            // Obtener el ID del usuario desde la sesión
            HttpSession session = request.getSession(false);
            Integer idUsuario = null;
            boolean logId = false; // Por defecto es false

            if (session != null && session.getAttribute("usuario") != null) {
                Usuario usuarioEnSesion = (Usuario) session.getAttribute("usuario");
                idUsuario = usuarioEnSesion.getIdUsuario();
                logId = true; // Si hay sesión activa, establecer logId como true
            }

            // Extraer nisOCedula del JsonObject si está presente
            String nisOCedula = jsonObject.containsKey("nis_cedula") && !jsonObject.isNull("nis_cedula")
                    ? jsonObject.getString("nis_cedula") : null;

            // Validar que nisOCedula sea obligatorio si no hay sesión activa
            if (idUsuario == null && (nisOCedula == null || nisOCedula.isEmpty())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Json.createObjectBuilder()
                            .add("error", "El campo nis_cedula es obligatorio si no está logueado.")
                            .build())
                        .build();
            }

            // Extraer página y tamaño de página del JsonObject
            int page = jsonObject.getInt("page", 1);  // Número de página (default 1)
            int size = jsonObject.getInt("size", 10); // Tamaño de página (default 10)

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

    
    @POST
    @Path("listarPagosPorServicio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagosPorServicio(JsonObject jsonObject, @Context HttpServletRequest request) {
        try {
            // Extraer el nombre del servicio
            String nombreServicio = jsonObject.getString("nombre_servicio", "N/A");
            Integer idUsuario = null;
            String nisOCedula = jsonObject.containsKey("nis_cedula") && !jsonObject.isNull("nis_cedula")
                                ? jsonObject.getString("nis_cedula") : null;

            // Obtener el ID del usuario desde la sesión
            HttpSession session = request.getSession(false);
            boolean logId = false; // Inicializar en false por defecto

            if (session != null) {
                Usuario usuarioEnSesion = (Usuario) session.getAttribute("usuario");
                if (usuarioEnSesion != null) {
                    idUsuario = usuarioEnSesion.getIdUsuario();
                    logId = true; // Establecer en true si hay un ID de usuario en la sesión
                }
            }

            // Validar nisOCedula si no hay sesión activa
            if (idUsuario == null && (nisOCedula == null || nisOCedula.isEmpty())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Json.createObjectBuilder()
                            .add("error", "El campo nis_cedula es obligatorio si no está logueado.")
                            .build())
                        .build();
            }

            int page = jsonObject.getInt("page", 1);  // Número de página (default 1)
            int size = jsonObject.getInt("size", 10); // Tamaño de página (default 10)

            Integer idServicio = servfacades.obtenerServicioPorNombre(nombreServicio);
            if (idServicio == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("error", "El nombre del servicio proporcionado no existe.").build()
                ).build();
            }

            // Lógica para obtener los pagos por servicio
            List<Pagos> pagos = pagfacade.listarPagosPorServicio(idUsuario, nisOCedula, idServicio, logId, page, size);
            int totalPagos = pagfacade.contarPagosPorServicio(idUsuario, nisOCedula, idServicio, logId); // Método adicional para contar todos los pagos

            // Construir la respuesta JSON con los pagos encontrados
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            if (pagos.isEmpty()) {
                responseBuilder.add("message", "Datos no encontrados");
            } else {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                pagos.forEach(pago -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                        .add("id_pago", pago.getIdPago())
                        .add("monto_pago", pago.getMontoPago().toString()) // Convertir BigDecimal a String
                        .add("fecha_pago", pago.getFechaPago().toString()));
                        //.add("nombre_servicio", pago.getNombreServicio()) // si tienes el nombre en la entidad
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
    
    
    @POST
    @Path("procesarPago")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarPagosPorServicio(JsonObject jsonObject, @Context HttpServletRequest request) {
        try {
             // Obtener el usuario autenticado desde la sesión
             HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Json.createObjectBuilder().add("error", "No estás logueado.").build())
                    .build();
            }

            Object usuarioEnSesion = session.getAttribute("usuario");
            if (!(usuarioEnSesion instanceof Usuario)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Json.createObjectBuilder().add("error", "Tipo de usuario incorrecto en la sesión.").build())
                    .build();
            }

            //Usuarios usuario = (Usuarios) usuarioEnSesion;
            
            Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuario");
            Integer idUsuarioAutenticado = usuarioAutenticado.getIdUsuario();
            
            System.out.println("ID USUARIO: " + idUsuarioAutenticado);

            //Integer idUsuario = jsonObject.getInt("id_usuario");
            Integer idDeuda = jsonObject.getInt("id_deuda");

            // Busca la deuda por su ID
            DeudasServicios deuda = deufacades.buscarDeudaPorId(idDeuda);
            if (deuda == null || !deuda.getIdUsuario().getIdUsuario().equals(idUsuarioAutenticado)) {
                return Response.status(Response.Status.NOT_FOUND).entity(
                    Json.createObjectBuilder().add("error", "No se encontró una deuda alguna para este usuario.").build()
                ).build();
            }
            
            System.out.println("Estado Deuda: " + deuda.getEstadoDeuda());

            // Verificar si la deuda ya está cancelada
            if ("Cancelado".equals(deuda.getEstadoDeuda())) {
                return Response.status(Response.Status.CONFLICT).entity(
                    Json.createObjectBuilder().add("message", "La deuda ya ha sido cancelada.").build()
                ).build();
            }

            BigDecimal montoDeuda = deuda.getMontoDeudaTotal();
            Servicios servicio =  deuda.getIdServicio();
            
            System.out.println("Monto Deuda: " + montoDeuda);
            System.out.println("ID servicio: " + servicio.getIdServicio());

            // Verificar si el usuario tiene saldo suficiente para pagar la deuda
            boolean saldoSuficiente = cufacades.tieneSaldoSuficiente(idUsuarioAutenticado, montoDeuda);

            if (saldoSuficiente) {
                // Procesar el pago (actualizar estado de la deuda, registrar el pago, etc.)
                Boolean pagoRealizado = pagfacade.registrarPago(idUsuarioAutenticado, idDeuda, servicio.getIdServicio(), montoDeuda);
                if (pagoRealizado) {
                    deufacades.actualizarDeuda(idDeuda, montoDeuda);
                    return Response.status(Response.Status.OK).entity(Json.createObjectBuilder()
                            .add("message", "El pago del servicio fue realizado exitosamente.").build()).build();
                }else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Json.createObjectBuilder()
                            .add("message", "Error al registrar el pago.").build()).build();
                }
            } else {
                 return Response.status(Response.Status.BAD_REQUEST).entity(Json.createObjectBuilder()
                         .add("message", "El usuario no tiene suficiente saldo para pagar la deuda.").build()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }
    }

}
