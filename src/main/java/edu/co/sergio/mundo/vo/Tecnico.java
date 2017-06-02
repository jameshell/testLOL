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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author james
 */
@Entity
@Table(name = "tecnico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tecnico.findAll", query = "SELECT t FROM Tecnico t"),
    @NamedQuery(name = "Tecnico.findByIdtecnico", query = "SELECT t FROM Tecnico t WHERE t.idtecnico = :idtecnico"),
    @NamedQuery(name = "Tecnico.findByNombretecnico", query = "SELECT t FROM Tecnico t WHERE t.nombretecnico = :nombretecnico")})
public class Tecnico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtecnico")
    private Integer idtecnico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombretecnico")
    private String nombretecnico;

    public Tecnico() {
    }

    public Tecnico(Integer idtecnico) {
        this.idtecnico = idtecnico;
    }

    public Tecnico(Integer idtecnico, String nombretecnico) {
        this.idtecnico = idtecnico;
        this.nombretecnico = nombretecnico;
    }

    public Integer getIdtecnico() {
        return idtecnico;
    }

    public void setIdtecnico(Integer idtecnico) {
        this.idtecnico = idtecnico;
    }

    public String getNombretecnico() {
        return nombretecnico;
    }

    public void setNombretecnico(String nombretecnico) {
        this.nombretecnico = nombretecnico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtecnico != null ? idtecnico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tecnico)) {
            return false;
        }
        Tecnico other = (Tecnico) object;
        if ((this.idtecnico == null && other.idtecnico != null) || (this.idtecnico != null && !this.idtecnico.equals(other.idtecnico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Tecnico[ idtecnico=" + idtecnico + " ]";
    }
    
}
