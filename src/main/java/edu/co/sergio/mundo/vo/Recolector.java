/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.vo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author james
 */
@Entity
@Table(name = "recolector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recolector.findAll", query = "SELECT r FROM Recolector r"),
    @NamedQuery(name = "Recolector.findByNombrerecolector", query = "SELECT r FROM Recolector r WHERE r.nombrerecolector = :nombrerecolector"),
    @NamedQuery(name = "Recolector.findByIdrecolector", query = "SELECT r FROM Recolector r WHERE r.idrecolector = :idrecolector")})
public class Recolector implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombrerecolector")
    private String nombrerecolector;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idrecolector")
    private Integer idrecolector;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrecolector")
    private Collection<Transaccionrecoleccion> transaccionrecoleccionCollection;

    public Recolector() {
    }

    public Recolector(Integer idrecolector) {
        this.idrecolector = idrecolector;
    }

    public Recolector(Integer idrecolector, String nombrerecolector) {
        this.idrecolector = idrecolector;
        this.nombrerecolector = nombrerecolector;
    }

    public String getNombrerecolector() {
        return nombrerecolector;
    }

    public void setNombrerecolector(String nombrerecolector) {
        this.nombrerecolector = nombrerecolector;
    }

    public Integer getIdrecolector() {
        return idrecolector;
    }

    public void setIdrecolector(Integer idrecolector) {
        this.idrecolector = idrecolector;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Transaccionrecoleccion> getTransaccionrecoleccionCollection() {
        return transaccionrecoleccionCollection;
    }

    public void setTransaccionrecoleccionCollection(Collection<Transaccionrecoleccion> transaccionrecoleccionCollection) {
        this.transaccionrecoleccionCollection = transaccionrecoleccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrecolector != null ? idrecolector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recolector)) {
            return false;
        }
        Recolector other = (Recolector) object;
        if ((this.idrecolector == null && other.idrecolector != null) || (this.idrecolector != null && !this.idrecolector.equals(other.idrecolector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Recolector[ idrecolector=" + idrecolector + " ]";
    }
    
}
