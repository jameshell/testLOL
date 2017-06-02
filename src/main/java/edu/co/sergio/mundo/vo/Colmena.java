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
 * @author james Alonso
 */
@Entity
@Table(name = "colmena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colmena.findAll", query = "SELECT c FROM Colmena c"),
    @NamedQuery(name = "Colmena.findByIdcolmena", query = "SELECT c FROM Colmena c WHERE c.idcolmena = :idcolmena"),
    @NamedQuery(name = "Colmena.findByPresenciademiel", query = "SELECT c FROM Colmena c WHERE c.presenciademiel = :presenciademiel"),
    @NamedQuery(name = "Colmena.findByPresenciadereina", query = "SELECT c FROM Colmena c WHERE c.presenciadereina = :presenciadereina"),
    @NamedQuery(name = "Colmena.findByNumpanalescera", query = "SELECT c FROM Colmena c WHERE c.numpanalescera = :numpanalescera"),
    @NamedQuery(name = "Colmena.findByNumpanalesalimento", query = "SELECT c FROM Colmena c WHERE c.numpanalesalimento = :numpanalesalimento"),
    @NamedQuery(name = "Colmena.findByNumpanalesconcria", query = "SELECT c FROM Colmena c WHERE c.numpanalesconcria = :numpanalesconcria"),
    @NamedQuery(name = "Colmena.findByNumpanalesvacios", query = "SELECT c FROM Colmena c WHERE c.numpanalesvacios = :numpanalesvacios")})
public class Colmena implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcolmena")
    private Integer idcolmena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "presenciademiel")
    private String presenciademiel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "presenciadereina")
    private String presenciadereina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numpanalescera")
    private int numpanalescera;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numpanalesalimento")
    private int numpanalesalimento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numpanalesconcria")
    private int numpanalesconcria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numpanalesvacios")
    private int numpanalesvacios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcolmena")
    private Collection<Transaccionrecoleccion> transaccionrecoleccionCollection;

    public Colmena() {
    }

    public Colmena(Integer idcolmena) {
        this.idcolmena = idcolmena;
    }

    public Colmena(Integer idcolmena, String presenciademiel, String presenciadereina, int numpanalescera, int numpanalesalimento, int numpanalesconcria, int numpanalesvacios) {
        this.idcolmena = idcolmena;
        this.presenciademiel = presenciademiel;
        this.presenciadereina = presenciadereina;
        this.numpanalescera = numpanalescera;
        this.numpanalesalimento = numpanalesalimento;
        this.numpanalesconcria = numpanalesconcria;
        this.numpanalesvacios = numpanalesvacios;
    }

    public Integer getIdcolmena() {
        return idcolmena;
    }

    public void setIdcolmena(Integer idcolmena) {
        this.idcolmena = idcolmena;
    }

    public String getPresenciademiel() {
        return presenciademiel;
    }

    public void setPresenciademiel(String presenciademiel) {
        this.presenciademiel = presenciademiel;
    }

    public String getPresenciadereina() {
        return presenciadereina;
    }

    public void setPresenciadereina(String presenciadereina) {
        this.presenciadereina = presenciadereina;
    }

    public int getNumpanalescera() {
        return numpanalescera;
    }

    public void setNumpanalescera(int numpanalescera) {
        this.numpanalescera = numpanalescera;
    }

    public int getNumpanalesalimento() {
        return numpanalesalimento;
    }

    public void setNumpanalesalimento(int numpanalesalimento) {
        this.numpanalesalimento = numpanalesalimento;
    }

    public int getNumpanalesconcria() {
        return numpanalesconcria;
    }

    public void setNumpanalesconcria(int numpanalesconcria) {
        this.numpanalesconcria = numpanalesconcria;
    }

    public int getNumpanalesvacios() {
        return numpanalesvacios;
    }

    public void setNumpanalesvacios(int numpanalesvacios) {
        this.numpanalesvacios = numpanalesvacios;
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
        hash += (idcolmena != null ? idcolmena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colmena)) {
            return false;
        }
        Colmena other = (Colmena) object;
        if ((this.idcolmena == null && other.idcolmena != null) || (this.idcolmena != null && !this.idcolmena.equals(other.idcolmena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sergio.mundo.vo.Colmena[ idcolmena=" + idcolmena + " ]";
    }
    
}
