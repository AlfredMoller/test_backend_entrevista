package facades;

import entities.DeudasServicios;
import entities.Servicios;
import java.math.BigDecimal;
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
     
     
     public DeudasServicios buscarDeudaPorId(Integer idDeuda) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d FROM DeudasServicios d ");
            sb.append("WHERE d.idDeuda = :idDeuda");

            Query query = em.createQuery(sb.toString(), DeudasServicios.class);
            query.setParameter("idDeuda", idDeuda);

            return (DeudasServicios) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Si no se encuentra la deuda, retornar null
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime la traza del error para fines de depuración
            throw new RuntimeException("Error al buscar la deuda por ID.", ex);
        }
    }
     
    
    // Función para actualizar la deuda
    public void actualizarDeuda(Integer idDeuda, BigDecimal montoPago) {
        try {
            StringBuilder sbActualizarDeuda = new StringBuilder();
            sbActualizarDeuda.append("UPDATE public.deudas_servicios ");
            sbActualizarDeuda.append("SET monto_abonado = monto_abonado + ?1, ");
            sbActualizarDeuda.append("estado_deuda = CASE ");
            sbActualizarDeuda.append("    WHEN monto_abonado + ?1 >= monto_deuda_total THEN 'Cancelado' ");
            sbActualizarDeuda.append("    ELSE estado_deuda ");
            sbActualizarDeuda.append("END ");
            sbActualizarDeuda.append("WHERE id_deuda = ?2");

            Query queryActualizarDeuda = em.createNativeQuery(sbActualizarDeuda.toString());
            queryActualizarDeuda.setParameter(1, montoPago);
            queryActualizarDeuda.setParameter(2, idDeuda);

            queryActualizarDeuda.executeUpdate();
        } catch (Exception ex) {
            LOGGER.error("Error al actualizar la deuda: {}", ex.getMessage());
            throw new RuntimeException("Error al actualizar la deuda.", ex);
        }
    }
}

