/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author james
 */
@Entity
@Table(name = "deposito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deposito.findAll", query = "SELECT d FROM Deposito d"),
    @NamedQuery(name = "Deposito.findByIddeposito", query = "SELECT d FROM Deposito d WHERE d.iddeposito = :iddeposito"),
    @NamedQuery(name = "Deposito.findByKgdemiel", query = "SELECT d FROM Deposito d WHERE d.kgdemiel = :kgdemiel")})
public class Deposito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "iddeposito")
    private Integer iddeposito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kgdemiel")
    private float kgdemiel;
    @JoinColumn(name = "idtransaccionrecoleccion", referencedColumnName = "idtransaccionrecoleccion")
    @ManyToOne(optional = false)
    private Transaccionrecoleccion idtransaccionrecoleccion;

    public Deposito() {
    }

    public Deposito(Integer iddeposito) {
        this.iddeposito = iddeposito;
    }

    public Deposito(Integer iddeposito, float kgdemiel) {
        this.iddeposito = iddeposito;
        this.kgdemiel = kgdemiel;
    }

    public Integer getIddeposito() {
        return iddeposito;
    }

    public void setIddeposito(Integer iddeposito) {
        this.iddeposito = iddeposito;
    }

    public float getKgdemiel() {
        return kgdemiel;
    }

    public void setKgdemiel(float kgdemiel) {
        this.kgdemiel = kgdemiel;
    }

    public Transaccionrecoleccion getIdtransaccionrecoleccion() {
        return idtransaccionrecoleccion;
    }

    public void setIdtransaccionrecoleccion(Transaccionrecoleccion idtransaccionrecoleccion) {
        this.idtransaccionrecoleccion = idtransaccionrecoleccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeposito != null ? iddeposito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deposito)) {
            return false;
        }
        Deposito other = (Deposito) object;
        if ((this.iddeposito == null && other.iddeposito != null) || (this.iddeposito != null && !this.iddeposito.equals(other.iddeposito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Deposito[ iddeposito=" + iddeposito + " ]";
    }
    
}
