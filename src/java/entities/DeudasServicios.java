/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "deudas_servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeudasServicios.findAll", query = "SELECT d FROM DeudasServicios d")
    , @NamedQuery(name = "DeudasServicios.findByIdDeuda", query = "SELECT d FROM DeudasServicios d WHERE d.idDeuda = :idDeuda")
    , @NamedQuery(name = "DeudasServicios.findByNumeroReferenciaComprobante", query = "SELECT d FROM DeudasServicios d WHERE d.numeroReferenciaComprobante = :numeroReferenciaComprobante")
    , @NamedQuery(name = "DeudasServicios.findByMontoDeudaTotal", query = "SELECT d FROM DeudasServicios d WHERE d.montoDeudaTotal = :montoDeudaTotal")
    , @NamedQuery(name = "DeudasServicios.findByMontoAbonado", query = "SELECT d FROM DeudasServicios d WHERE d.montoAbonado = :montoAbonado")
    , @NamedQuery(name = "DeudasServicios.findByFechaVencimiento", query = "SELECT d FROM DeudasServicios d WHERE d.fechaVencimiento = :fechaVencimiento")})
public class DeudasServicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deuda")
    private Integer idDeuda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_referencia_comprobante")
    private String numeroReferenciaComprobante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_deuda_total")
    private BigDecimal montoDeudaTotal;
    @Column(name = "monto_abonado")
    private BigDecimal montoAbonado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Servicios idServicio;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public DeudasServicios() {
    }

    public DeudasServicios(Integer idDeuda) {
        this.idDeuda = idDeuda;
    }

    public DeudasServicios(Integer idDeuda, String numeroReferenciaComprobante, BigDecimal montoDeudaTotal, Date fechaVencimiento) {
        this.idDeuda = idDeuda;
        this.numeroReferenciaComprobante = numeroReferenciaComprobante;
        this.montoDeudaTotal = montoDeudaTotal;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(Integer idDeuda) {
        this.idDeuda = idDeuda;
    }

    public String getNumeroReferenciaComprobante() {
        return numeroReferenciaComprobante;
    }

    public void setNumeroReferenciaComprobante(String numeroReferenciaComprobante) {
        this.numeroReferenciaComprobante = numeroReferenciaComprobante;
    }

    public BigDecimal getMontoDeudaTotal() {
        return montoDeudaTotal;
    }

    public void setMontoDeudaTotal(BigDecimal montoDeudaTotal) {
        this.montoDeudaTotal = montoDeudaTotal;
    }

    public BigDecimal getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(BigDecimal montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Servicios getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicios idServicio) {
        this.idServicio = idServicio;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeuda != null ? idDeuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeudasServicios)) {
            return false;
        }
        DeudasServicios other = (DeudasServicios) object;
        if ((this.idDeuda == null && other.idDeuda != null) || (this.idDeuda != null && !this.idDeuda.equals(other.idDeuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DeudasServicios[ idDeuda=" + idDeuda + " ]";
    }
    
}
