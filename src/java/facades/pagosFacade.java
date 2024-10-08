package facades;

import entities.Pagos;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class pagosFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(pagosFacade.class);

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public List<Pagos> listarPagosPorFecha(Integer idUsuario, String nisOCedula, Date fechaInicio, Date fechaFin, boolean logId, int page, int size) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p ");
            sb.append("FROM Pagos p ");
            sb.append("WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin ");
            sb.append("AND p.estadoPago = 'Pagado' ");

            if (logId && idUsuario != null) {
                sb.append("AND p.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND p.idDeuda.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString(), Pagos.class)
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
    
    
    public List<Pagos> listarPagosPorServicio(Integer idUsuario, String nisOCedula, Integer idServicio, boolean logId, int page, int size) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p ");
            sb.append("FROM Pagos p ");
            sb.append("WHERE p.idServicio.idServicio = :idServicio ");
            sb.append("AND p.estadoPago = 'Pagado' ");

            if (logId && idUsuario != null) {
                sb.append("AND p.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND p.idDeuda.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString(), Pagos.class)
                            .setParameter("idServicio", idServicio);

            if (logId && idUsuario != null) {
                query.setParameter("idUsuario", idUsuario);
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } catch (Exception ex) {
            LOGGER.error("Error al listar los pagos por servicio: {}", ex.getMessage());
            throw new RuntimeException("Error al listar los pagos por servicio.", ex);
        }
    }


    public int contarPagos(Integer idUsuario, String nisOCedula, Date fechaInicio, Date fechaFin, boolean logId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(p) ");
            sb.append("FROM Pagos p ");
            sb.append("WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin ");
            sb.append("AND p.estadoPago = 'Pagado' ");

            if (logId && idUsuario != null) {
                sb.append("AND p.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND p.idDeuda.numeroReferenciaComprobante = :nisOCedula ");
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
    
    
    public int contarPagosPorServicio(Integer idUsuario, String nisOCedula, Integer idServicio, boolean logId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(p) ");
            sb.append("FROM Pagos p ");
            sb.append("WHERE p.idServicio.idServicio = :idServicio ");
            sb.append("AND p.estadoPago = 'Pagado' ");  // Asumiendo que el estado de pago es 'Pagado'

            if (logId && idUsuario != null) {
                sb.append("AND p.idUsuario.idUsuario = :idUsuario ");
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                sb.append("AND p.idDeuda.numeroReferenciaComprobante = :nisOCedula ");
            }

            Query query = em.createQuery(sb.toString())
                            .setParameter("idServicio", idServicio);

            if (logId && idUsuario != null) {
                query.setParameter("idUsuario", idUsuario);
            } else if (!logId && nisOCedula != null && !nisOCedula.isEmpty()) {
                query.setParameter("nisOCedula", nisOCedula);
            }

            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            LOGGER.error("Error al contar los pagos por servicio: {}", ex.getMessage());
            throw new RuntimeException("No se pudo contar los pagos por servicio.", ex);
        }
    }
    
    
    public boolean registrarPago(Integer idUsuario, Integer idDeuda, Integer idServicio, BigDecimal montoPago) {
        try {
            StringBuilder sbInsertPago = new StringBuilder();
            sbInsertPago.append("INSERT INTO public.pagos (id_usuario, id_deuda, id_servicio, monto_pago, fecha_pago, estado_pago) ");
            sbInsertPago.append("VALUES (?1, ?2, ?3, ?4, CURRENT_TIMESTAMP, 'Pagado')");

            Query queryInsertPago = em.createNativeQuery(sbInsertPago.toString());
            queryInsertPago.setParameter(1, idUsuario);
            queryInsertPago.setParameter(2, idDeuda);
            queryInsertPago.setParameter(3, idServicio);
            queryInsertPago.setParameter(4, montoPago);

            int registroExitoso = queryInsertPago.executeUpdate();
            return registroExitoso > 0 ; // Devuelve true si el registro fue exitoso                   
        } catch (Exception ex) {
            LOGGER.error("Error al registrar el pago: {}", ex.getMessage());
            throw new RuntimeException("Error al registrar el pago.", ex);
        }
    }

}
