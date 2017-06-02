/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author james
 */
@Entity
@Table(name = "transaccionrecoleccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccionrecoleccion.findAll", query = "SELECT t FROM Transaccionrecoleccion t"),
    @NamedQuery(name = "Transaccionrecoleccion.findByFecharecoleccion", query = "SELECT t FROM Transaccionrecoleccion t WHERE t.fecharecoleccion = :fecharecoleccion"),
    @NamedQuery(name = "Transaccionrecoleccion.findByIdtransaccionrecoleccion", query = "SELECT t FROM Transaccionrecoleccion t WHERE t.idtransaccionrecoleccion = :idtransaccionrecoleccion")})
public class Transaccionrecoleccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecharecoleccion")
    @Temporal(TemporalType.DATE)
    private Date fecharecoleccion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtransaccionrecoleccion")
    private Integer idtransaccionrecoleccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtransaccionrecoleccion")
    private Collection<Deposito> depositoCollection;
    @JoinColumn(name = "idcolmena", referencedColumnName = "idcolmena")
    @ManyToOne(optional = false)
    private Colmena idcolmena;
    @JoinColumn(name = "idrecolector", referencedColumnName = "idrecolector")
    @ManyToOne(optional = false)
    private Recolector idrecolector;

    public Transaccionrecoleccion() {
    }

    public Transaccionrecoleccion(Integer idtransaccionrecoleccion) {
        this.idtransaccionrecoleccion = idtransaccionrecoleccion;
    }

    public Transaccionrecoleccion(Integer idtransaccionrecoleccion, Date fecharecoleccion) {
        this.idtransaccionrecoleccion = idtransaccionrecoleccion;
        this.fecharecoleccion = fecharecoleccion;
    }

    public Date getFecharecoleccion() {
        return fecharecoleccion;
    }

    public void setFecharecoleccion(Date fecharecoleccion) {
        this.fecharecoleccion = fecharecoleccion;
    }

    public Integer getIdtransaccionrecoleccion() {
        return idtransaccionrecoleccion;
    }

    public void setIdtransaccionrecoleccion(Integer idtransaccionrecoleccion) {
        this.idtransaccionrecoleccion = idtransaccionrecoleccion;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Deposito> getDepositoCollection() {
        return depositoCollection;
    }

    public void setDepositoCollection(Collection<Deposito> depositoCollection) {
        this.depositoCollection = depositoCollection;
    }

    public Colmena getIdcolmena() {
        return idcolmena;
    }

    public void setIdcolmena(Colmena idcolmena) {
        this.idcolmena = idcolmena;
    }

    public Recolector getIdrecolector() {
        return idrecolector;
    }

    public void setIdrecolector(Recolector idrecolector) {
        this.idrecolector = idrecolector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccionrecoleccion != null ? idtransaccionrecoleccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccionrecoleccion)) {
            return false;
        }
        Transaccionrecoleccion other = (Transaccionrecoleccion) object;
        if ((this.idtransaccionrecoleccion == null && other.idtransaccionrecoleccion != null) || (this.idtransaccionrecoleccion != null && !this.idtransaccionrecoleccion.equals(other.idtransaccionrecoleccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Transaccionrecoleccion[ idtransaccionrecoleccion=" + idtransaccionrecoleccion + " ]";
    }
    
}
