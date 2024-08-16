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
     
     
     public List<DeudasServicios> listarPagosPorFecha(Integer idUsuario, String nisOCedula, Date fechaInicio, Date fechaFin, boolean logId, int page, int size) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d ");
            sb.append("FROM DeudasServicios d ");
            sb.append("WHERE d.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin ");
            sb.append("AND d.estadoDeuda = 'Pagado' ");

            if (logId && idUsuario != null) {
                sb.append("AND d.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND d.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString(), DeudasServicios.class)
                            .setParameter("fechaInicio", fechaInicio)
                            .setParameter("fechaFin", fechaFin);

            if (logId && idUsuario != null) {
                query.setParameter("idUsuario", idUsuario);
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error al listar los pagos por fecha: {}", ex.getMessage());
            throw new RuntimeException("Error al listar los pagos por fecha.", ex);
        }
    }
     
     
    public int contarPagos(Integer idUsuario, String nisOCedula, Date fechaInicio, Date fechaFin, boolean logId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(d) ");
            sb.append("FROM DeudasServicios d ");
            sb.append("WHERE d.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin ");
            sb.append("AND d.estadoDeuda = 'Pagado' ");

            if (logId && idUsuario != null) {
                sb.append("AND d.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND d.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString())
                            .setParameter("fechaInicio", fechaInicio)
                            .setParameter("fechaFin", fechaFin);

            if (logId && idUsuario != null) {
                query.setParameter("idUsuario", idUsuario);
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            LOGGER.error("Error al contar los pagos: {}", ex.getMessage());
            throw new RuntimeException("No se pudo contar los pagos.", ex);
        }
    }



}

