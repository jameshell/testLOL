/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author james
 */
@Entity
@Table(name = "transaccionregistro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccionregistro.findAll", query = "SELECT t FROM Transaccionregistro t"),
    @NamedQuery(name = "Transaccionregistro.findByIdtransaccionregistro", query = "SELECT t FROM Transaccionregistro t WHERE t.idtransaccionregistro = :idtransaccionregistro"),
    @NamedQuery(name = "Transaccionregistro.findByIdcolmena", query = "SELECT t FROM Transaccionregistro t WHERE t.idcolmena = :idcolmena"),
    @NamedQuery(name = "Transaccionregistro.findByIdtecnico", query = "SELECT t FROM Transaccionregistro t WHERE t.idtecnico = :idtecnico"),
    @NamedQuery(name = "Transaccionregistro.findByFecharegistro", query = "SELECT t FROM Transaccionregistro t WHERE t.fecharegistro = :fecharegistro")})
public class Transaccionregistro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtransaccionregistro")
    private Integer idtransaccionregistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcolmena")
    private int idcolmena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtecnico")
    private int idtecnico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecharegistro")
    @Temporal(TemporalType.DATE)
    private Date fecharegistro;

    public Transaccionregistro() {
    }

    public Transaccionregistro(Integer idtransaccionregistro) {
        this.idtransaccionregistro = idtransaccionregistro;
    }

    public Transaccionregistro(Integer idtransaccionregistro, int idcolmena, int idtecnico, Date fecharegistro) {
        this.idtransaccionregistro = idtransaccionregistro;
        this.idcolmena = idcolmena;
        this.idtecnico = idtecnico;
        this.fecharegistro = fecharegistro;
    }

    public Integer getIdtransaccionregistro() {
        return idtransaccionregistro;
    }

    public void setIdtransaccionregistro(Integer idtransaccionregistro) {
        this.idtransaccionregistro = idtransaccionregistro;
    }

    public int getIdcolmena() {
        return idcolmena;
    }

    public void setIdcolmena(int idcolmena) {
        this.idcolmena = idcolmena;
    }

    public int getIdtecnico() {
        return idtecnico;
    }

    public void setIdtecnico(int idtecnico) {
        this.idtecnico = idtecnico;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccionregistro != null ? idtransaccionregistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccionregistro)) {
            return false;
        }
        Transaccionregistro other = (Transaccionregistro) object;
        if ((this.idtransaccionregistro == null && other.idtransaccionregistro != null) || (this.idtransaccionregistro != null && !this.idtransaccionregistro.equals(other.idtransaccionregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Transaccionregistro[ idtransaccionregistro=" + idtransaccionregistro + " ]";
    }
    
}
