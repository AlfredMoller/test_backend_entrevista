package facades;

import entities.Servicios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class serviciosFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(serviciosFacade.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public Integer obtenerServicioPorNombre(String nombreServicio) {

        LOGGER.info("Servicio Recibido: {}", nombreServicio);
        System.out.println("Nombre Servicio 2: " + nombreServicio);

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT s.idServicio "); // Usar el nombre del atributo en la entidad
            sb.append("FROM Servicios s "); // Usar el nombre de la entidad, no de la tabla
            sb.append("WHERE s.nombreServicio = ?1"); // Usar el nombre del atributo en la entidad

            Query query = em.createQuery(sb.toString());
            query.setParameter(1, nombreServicio); // Configurar el parámetro posicional

            return (Integer) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn("No se encontró servicio con el nombre: {}", nombreServicio);
            return null;
        } catch (Exception ex) {
            LOGGER.error("Error al buscar el servicio por nombre: {}", ex.getMessage());
            throw new RuntimeException("Error al buscar el servicio por nombre.", ex);
        }
    }
}
