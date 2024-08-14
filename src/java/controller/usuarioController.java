/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import models.Usuario;
import facades.usuarioFacades;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Path("usuario")
public class usuarioController {

    @EJB
    private usuarioFacades usfacades;
    
    
    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128); // 65536 iteraciones, 128 bits de clave
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Genera un salt a partir de una frase o clave predefinida
    public static byte[] generateSaltFromPhrase(String phrase) {
        try {
            // Convierte la frase a un array de bytes
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt = md.digest(phrase.getBytes(StandardCharsets.UTF_8));

            // Retorna los primeros 16 bytes del hash como salt
            return Arrays.copyOf(salt, 16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el salt a partir de la frase", e);
        }
    }


    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(JsonObject jsonObject) {
        try{
            System.out.println("[TEST]");
            String saltPhrase = "miFraseSecretaParaElSalt";
            byte[] salt = generateSaltFromPhrase(saltPhrase);;

            // Extraer valores del JsonObject
            String nombreUsuario = jsonObject.getString("nombre_usuario", "N/A");
            String apellidoUsuario = jsonObject.getString("apellido_usuario", "N/A");
            String emailUsuario = jsonObject.getString("email_usuario", "N/A");

            String claveUsuario = hashPassword(jsonObject.getString("clave_usuario"), salt);
            String telefonoUsuario = jsonObject.getString("telefono_usuario", "N/A");

            // Generar un UUID aleatorio y convertirlo a String
            UUID uuidUsuario = UUID.randomUUID();
            String uuidUsuarioString = uuidUsuario.toString(); // Convertir UUID a String

            int idCiudad = jsonObject.getInt("id_ciudad", 0);
            String cedulaUsuario = jsonObject.getString("cedula_usuario", "N/A");
            
            Boolean existeUsuario = usfacades.verifExistUsuario(cedulaUsuario);
            if (existeUsuario) {
                JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("error", "Un usuario con la información proporcionada ya existe.")
                    .build();
                return Response.status(Response.Status.CONFLICT).entity(jsonResponse).build();
            }

            // Imprimir los valores para verificar
            System.out.println("Nombre: " + nombreUsuario);
            System.out.println("Apellido: " + apellidoUsuario);
            System.out.println("Email: " + emailUsuario);
            System.out.println("Teléfono: " + telefonoUsuario);
            System.out.println("UUID: " + uuidUsuarioString);
            System.out.println("ID Ciudad: " + idCiudad);
            System.out.println("Cédula: " + cedulaUsuario);

            // Crear el objeto Usuario y setear los valores
            Usuario usuario = new Usuario();
            usuario.setNombre_usuario(nombreUsuario);
            usuario.setApellido_usuario(apellidoUsuario);
            usuario.setEmail_usuario(emailUsuario);
            usuario.setClave_usuario(claveUsuario);
            usuario.setTelefono_usuario(telefonoUsuario);
            usuario.setUuid_usuario(uuidUsuarioString); // Asignar el UUID convertido a String
            usuario.setId_ciudad(idCiudad);
            usuario.setCedula_usuario(cedulaUsuario);

            // Llamar al Facade para registrar el usuario
            if (usfacades != null) {
                System.out.println("Llamando al Facade...");
                usfacades.registrarUsuario(usuario);
            } else {
                System.out.println("El Facade no fue inyectado correctamente.");
            }

            // Crear respuesta JSON
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("message", "Usuario " + nombreUsuario + " registrado con éxito!")
                .build();

            return Response.ok(jsonResponse).build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al procesar la solicitud: " + e.getMessage())
                .build();
        }    
    }
    
    
    /*@POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response LoginUsuario(JsonObject jsonObject) {
        return
    }*/
}
