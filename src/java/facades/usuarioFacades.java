package facades;

import models.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pc
 */

@Stateless
public class usuarioFacades {

    private static final Logger LOGGER = LoggerFactory.getLogger(usuarioFacades.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public void registrarUsuario(Usuario usuario) {
        String claveUsuario = usuario.getClave_usuario();
        
        // Imprimir el nombre recibido usando Logger y System.out
        LOGGER.info("Clave Recibida: {}", claveUsuario);
        System.out.println("Nombre Recibido: " + claveUsuario);
        
        try {
            LOGGER.info("Preparando consulta nativa para insertar el usuario.");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO public.usuario (nombre_usuario, apellido_usuario, email_usuario, clave_usuario ,telefono_usuario, id_ciudad, cedula_usuario) ");
            sb.append("VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            Query insertUsuario = em.createNativeQuery(sb.toString());
            insertUsuario.setParameter(1, usuario.getNombre_usuario());
            insertUsuario.setParameter(2, usuario.getApellido_usuario());
            insertUsuario.setParameter(3, usuario.getEmail_usuario());
            insertUsuario.setParameter(4, usuario.getClave_usuario());
            insertUsuario.setParameter(5, usuario.getTelefono_usuario());
            //insertUsuario.setParameter(5, usuario.getUuid_usuario());
            insertUsuario.setParameter(6, usuario.getId_ciudad());
            insertUsuario.setParameter(7, usuario.getCedula_usuario());
            
            int result = insertUsuario.executeUpdate();
            em.flush();

            if (result == 0) {
                LOGGER.error("[NO SE GUARDÓ] No se pudo insertar el usuario en la base de datos.");
                throw new RuntimeException("Problemas al almacenar el usuario!");
            }

            LOGGER.info("Usuario {} registrado con éxito en la base de datos.", usuario.getNombre_usuario());
        } catch (Exception ex) {
            LOGGER.error("Error al registrar el usuario {}: {}", usuario.getNombre_usuario(), ex.getMessage());
            // Puedes lanzar una excepción personalizada o manejarla de otra forma
            throw new RuntimeException("No se pudo registrar el usuario.", ex);
        }
    }
    
     public boolean verifExistUsuario(String cedulaUsuario) {
        System.out.println("Cedula Recibido: " + cedulaUsuario);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(u.cedula_usuario) ");
            sb.append("FROM public.usuario u ");
            sb.append("WHERE u.cedula_usuario = ?1");
            
            Query query = em.createNativeQuery(sb.toString());
            query.setParameter(1, cedulaUsuario);
            
            Long count = ((Number) query.getSingleResult()).longValue();
            return count > 0;
        } catch (Exception ex) {
            LOGGER.error("No se pudo verificar la existencia del usuario. {}", ex.getMessage());
            throw new RuntimeException("No se pudo verificar la existencia del usuario.", ex);
        }
    }
}
