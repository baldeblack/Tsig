/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vane
 */
@Entity
@Table(name = "imagenes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imagenes.findAll", query = "SELECT i FROM Imagenes i"),
    @NamedQuery(name = "Imagenes.findByIdImg", query = "SELECT i FROM Imagenes i WHERE i.idImg = :idImg"),
    @NamedQuery(name = "Imagenes.findByRuta", query = "SELECT i FROM Imagenes i WHERE i.ruta = :ruta"),
    @NamedQuery(name = "Imagenes.findByDestacada", query = "SELECT i FROM Imagenes i WHERE i.destacada = :destacada")})
public class Imagenes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_img")
    private Integer idImg;
    @Size(max = 200)
    @Column(name = "ruta")
    private String ruta;
    @Column(name = "destacada")
    private Boolean destacada;
    @JoinColumn(name = "gid_inm", referencedColumnName = "gid_inm")
    @ManyToOne
    private Inmueble gidInm;

    public Imagenes() {
    }

    public Imagenes(String ruta, boolean destacada, Object gidInm){
        this.setRuta(ruta);
        this.setDestacada(destacada);
        this.setGidInm((Inmueble) gidInm);
    }
    
    
    public Imagenes(Integer idImg) {
        this.idImg = idImg;
    }

    public Integer getIdImg() {
        return idImg;
    }

    public void setIdImg(Integer idImg) {
        this.idImg = idImg;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Boolean getDestacada() {
        return destacada;
    }

    public void setDestacada(Boolean destacada) {
        this.destacada = destacada;
    }

    public Inmueble getGidInm() {
        return gidInm;
    }

    public void setGidInm(Inmueble gidInm) {
        this.gidInm = gidInm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImg != null ? idImg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagenes)) {
            return false;
        }
        Imagenes other = (Imagenes) object;
        if ((this.idImg == null && other.idImg != null) || (this.idImg != null && !this.idImg.equals(other.idImg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Imagenes[ idImg=" + idImg + " ]";
    }
    
}
