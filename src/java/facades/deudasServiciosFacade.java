/*package facades;

import entities.DeudasServicios;
import java.util.ArrayList;
import java.util.List; // Asegúrate de que esta línea esté presente
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class deudasServiciosFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(deudasServiciosFacade.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public Boolean listarDeudas(int idUsuario, int idServicio, String nisOCedula, boolean logId) {
    
        System.out.println("[ID Usuario - Lista]: " + idUsuario);
        System.out.println("[ID Servicio - Lista]: " + idServicio);

        try {
            // Comentado para realizar la prueba
            /*StringBuilder sb = new StringBuilder();
            sb.append("SELECT du ");
            sb.append("FROM DeudasServicios du ");
            sb.append("WHERE du.idServicio.idServicio = :idServicio ");

            if (logId) {
                sb.append("AND du.idUsuario.idUsuario = :idUsuario ");
            } 
            else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND du.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString(), DeudasServicios.class);
            query.setParameter("idServicio", idServicio);

            if (logId) {
                query.setParameter("idUsuario", idUsuario);
            } else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            return query.getResultList();*/

            // Retornar una lista vacía
          /*  return true;
        } catch (Exception ex) {
            ex.printStackTrace(); // Esto imprimirá el stack trace completo en el log
            LOGGER.error("Error al filtrar la deuda: {}", ex.getMessage());
            throw new RuntimeException("No se pudo filtrar la deuda.", ex);
        }
    }
}*/

package facades;

import entities.DeudasServicios;
import entities.Servicios;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class deudasServiciosFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(serviciosFacade.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;
    
    public List<DeudasServicios> listarDeaudasByParams(Integer idUsuario, int idServicio, String nisOCedula, boolean logId, int page, int size) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT du FROM DeudasServicios du WHERE du.idServicio.idServicio = :idServicio ");
            if (logId) {
                sb.append("AND du.idUsuario.idUsuario = :idUsuario ");
            } else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND du.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString(), DeudasServicios.class)
                            .setParameter("idServicio", idServicio)
                            .setFirstResult((page - 1) * size)  // Paginación - primer resultado
                            .setMaxResults(size);  // Paginación - tamaño de página

            if (logId) {
                query.setParameter("idUsuario", idUsuario);
            } else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(); 
            throw new RuntimeException("No se pudo filtrar la deuda.", ex);
        }
    }

    public int contarDeudas(Integer idUsuario, int idServicio, String nisOCedula, boolean logId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(du) FROM DeudasServicios du WHERE du.idServicio.idServicio = :idServicio ");
            if (logId) {
                sb.append("AND du.idUsuario.idUsuario = :idUsuario ");
            } else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND du.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString())
                            .setParameter("idServicio", idServicio);

            if (logId) {
                query.setParameter("idUsuario", idUsuario);
            } else if (nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            ex.printStackTrace(); 
            throw new RuntimeException("No se pudo contar las deudas.", ex);
        }
    }
    
     public DeudasServicios buscarDeudaPorFechaYServicio(Integer idUsuario, Integer idServicio, Date fechaDeuda) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT du ");
            sb.append("FROM DeudasServicios du ");
            sb.append("WHERE du.idServicio.idServicio = :idServicio ");
            sb.append("AND du.idUsuario.idUsuario = :idUsuario ");
            sb.append("AND du.fechaVencimiento = :fechaDeuda");

            Query query = em.createQuery(sb.toString(), DeudasServicios.class);
            query.setParameter("idServicio", idServicio);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("fechaDeuda", fechaDeuda);

            return (DeudasServicios) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn("No se encontró deuda para el servicio, usuario y fecha especificados.");
            return null;
        } catch (Exception ex) {
            LOGGER.error("Error al buscar la deuda: {}", ex.getMessage());
            throw new RuntimeException("Error al buscar la deuda.", ex);
        }
    }

}

