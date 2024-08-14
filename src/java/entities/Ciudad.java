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
@Table(name = "ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c")
    , @NamedQuery(name = "Ciudad.findByIdCiudad", query = "SELECT c FROM Ciudad c WHERE c.idCiudad = :idCiudad")
    , @NamedQuery(name = "Ciudad.findByNombreCiudad", query = "SELECT c FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad")
    , @NamedQuery(name = "Ciudad.findByCodigoDistrito", query = "SELECT c FROM Ciudad c WHERE c.codigoDistrito = :codigoDistrito")
    , @NamedQuery(name = "Ciudad.findByIdDepartamento", query = "SELECT c FROM Ciudad c WHERE c.idDepartamento = :idDepartamento")})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ciudad")
    private Integer idCiudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_ciudad")
    private String nombreCiudad;
    @Column(name = "codigo_distrito")
    private Integer codigoDistrito;
    @Column(name = "id_departamento")
    private Integer idDepartamento;

    public Ciudad() {
    }

    public Ciudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Ciudad(Integer idCiudad, String nombreCiudad) {
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public Integer getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(Integer codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiudad != null ? idCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.idCiudad == null && other.idCiudad != null) || (this.idCiudad != null && !this.idCiudad.equals(other.idCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ciudad[ idCiudad=" + idCiudad + " ]";
    }
    
}
