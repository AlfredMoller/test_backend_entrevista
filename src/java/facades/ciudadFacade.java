/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;
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
public class ciudadFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(ciudadFacade.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public boolean verifCiudadById(int idCiudad) {
        System.out.println("IdCiudad Recibido: " + idCiudad);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(c.id_ciudad) ");
            sb.append("FROM public.ciudad c ");
            sb.append("WHERE c.id_ciudad = ?1");

            Query query = em.createNativeQuery(sb.toString());
            query.setParameter(1, idCiudad);

            Long count = ((Number) query.getSingleResult()).longValue();
            return count > 0;
        } catch (Exception ex) {
            LOGGER.error("No se pudo verificar la existencia de la ciudad. {}", ex.getMessage());
            throw new RuntimeException("No se pudo verificar la existencia de la ciudad.", ex);
        }
    }
}

