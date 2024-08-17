package facades;

import entities.Cuentas;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class cuentasfacade {

    @PersistenceContext(unitName = "pago_serviciosPU")
    private EntityManager em;

    public boolean tieneSaldoSuficiente(int idUsuario, BigDecimal montoDeuda) {
        try {
            // Construcción de la consulta para sumar los saldos de todas las cuentas del usuario
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT SUM(c.saldo) ");
            sb.append("FROM Cuentas c ");
            sb.append("WHERE c.idUsuario.idUsuario = :idUsuario");

            Query query = em.createQuery(sb.toString());
            query.setParameter("idUsuario", idUsuario);

            BigDecimal saldoTotal = (BigDecimal) query.getSingleResult();
            
            // Comparar el saldo total con el monto de la deuda
            return saldoTotal != null && saldoTotal.compareTo(montoDeuda) >= 0;
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime la traza del error para fines de depuración
            throw new RuntimeException("Error al verificar el saldo del usuario.", ex);
        }
    }
}
