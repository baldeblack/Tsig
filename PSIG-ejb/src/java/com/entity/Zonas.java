/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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

/**
 *
 * @author vane
 */
@Entity
@Table(name = "zonas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zonas.findAll", query = "SELECT z FROM Zonas z"),
    @NamedQuery(name = "Zonas.findByGidzona", query = "SELECT z FROM Zonas z WHERE z.gidzona = :gidzona"),
    @NamedQuery(name = "Zonas.findByNombre", query = "SELECT z FROM Zonas z WHERE z.nombre = :nombre")})
public class Zonas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "gidzona")
    private Integer gidzona;
    @Size(max = 20)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "gidzona")
    private Collection<Demanda> demandaCollection;
    @OneToMany(mappedBy = "gidzona")
    private Collection<Inmueble> inmuebleCollection;

    public Zonas() {
    }

    public Zonas(Integer gidzona) {
        this.gidzona = gidzona;
    }

    public Integer getGidzona() {
        return gidzona;
    }

    public void setGidzona(Integer gidzona) {
        this.gidzona = gidzona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Demanda> getDemandaCollection() {
        return demandaCollection;
    }

    public void setDemandaCollection(Collection<Demanda> demandaCollection) {
        this.demandaCollection = demandaCollection;
    }

    @XmlTransient
    public Collection<Inmueble> getInmuebleCollection() {
        return inmuebleCollection;
    }

    public void setInmuebleCollection(Collection<Inmueble> inmuebleCollection) {
        this.inmuebleCollection = inmuebleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gidzona != null ? gidzona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zonas)) {
            return false;
        }
        Zonas other = (Zonas) object;
        if ((this.gidzona == null && other.gidzona != null) || (this.gidzona != null && !this.gidzona.equals(other.gidzona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Zonas[ gidzona=" + gidzona + " ]";
    }
    
}
