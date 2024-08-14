/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByIdPais", query = "SELECT p FROM Pais p WHERE p.idPais = :idPais")
    , @NamedQuery(name = "Pais.findByNombrePais", query = "SELECT p FROM Pais p WHERE p.nombrePais = :nombrePais")
    , @NamedQuery(name = "Pais.findByCodigoIsoPais2", query = "SELECT p FROM Pais p WHERE p.codigoIsoPais2 = :codigoIsoPais2")
    , @NamedQuery(name = "Pais.findByCodigoIsoPais3", query = "SELECT p FROM Pais p WHERE p.codigoIsoPais3 = :codigoIsoPais3")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pais")
    private Integer idPais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_pais")
    private String nombrePais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_iso_pais2")
    private String codigoIsoPais2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codigo_iso_pais3")
    private String codigoIsoPais3;

    public Pais() {
    }

    public Pais(Integer idPais) {
        this.idPais = idPais;
    }

    public Pais(Integer idPais, String nombrePais, String codigoIsoPais2, String codigoIsoPais3) {
        this.idPais = idPais;
        this.nombrePais = nombrePais;
        this.codigoIsoPais2 = codigoIsoPais2;
        this.codigoIsoPais3 = codigoIsoPais3;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getCodigoIsoPais2() {
        return codigoIsoPais2;
    }

    public void setCodigoIsoPais2(String codigoIsoPais2) {
        this.codigoIsoPais2 = codigoIsoPais2;
    }

    public String getCodigoIsoPais3() {
        return codigoIsoPais3;
    }

    public void setCodigoIsoPais3(String codigoIsoPais3) {
        this.codigoIsoPais3 = codigoIsoPais3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPais != null ? idPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.idPais == null && other.idPais != null) || (this.idPais != null && !this.idPais.equals(other.idPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pais[ idPais=" + idPais + " ]";
    }
    
}
