/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vane
 */
@Entity
@Table(name = "demanda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Demanda.findAll", query = "SELECT d FROM Demanda d"),
    @NamedQuery(name = "Demanda.findByIdDemanda", query = "SELECT d FROM Demanda d WHERE d.idDemanda = :idDemanda"),
    @NamedQuery(name = "Demanda.findByFecha", query = "SELECT d FROM Demanda d WHERE d.fecha = :fecha")})
public class Demanda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_demanda")
    private Integer idDemanda;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "gidzona", referencedColumnName = "gidzona")
    @ManyToOne
    private Zonas gidzona;

    public Demanda() {
    }

    public Demanda(Integer idDemanda) {
        this.idDemanda = idDemanda;
    }

    public Integer getIdDemanda() {
        return idDemanda;
    }

    public void setIdDemanda(Integer idDemanda) {
        this.idDemanda = idDemanda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Zonas getGidzona() {
        return gidzona;
    }

    public void setGidzona(Zonas gidzona) {
        this.gidzona = gidzona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDemanda != null ? idDemanda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demanda)) {
            return false;
        }
        Demanda other = (Demanda) object;
        if ((this.idDemanda == null && other.idDemanda != null) || (this.idDemanda != null && !this.idDemanda.equals(other.idDemanda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Demanda[ idDemanda=" + idDemanda + " ]";
    }
    
}
