/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "inmueble")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inmueble.findAll", query = "SELECT i FROM Inmueble i"),
    @NamedQuery(name = "Inmueble.findByGidInm", query = "SELECT i FROM Inmueble i WHERE i.gidInm = :gidInm"),
    @NamedQuery(name = "Inmueble.findByProposito", query = "SELECT i FROM Inmueble i WHERE i.proposito = :proposito"),
    @NamedQuery(name = "Inmueble.findByEstado", query = "SELECT i FROM Inmueble i WHERE i.estado = :estado"),
    @NamedQuery(name = "Inmueble.findByTipo", query = "SELECT i FROM Inmueble i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "Inmueble.findByValormin", query = "SELECT i FROM Inmueble i WHERE i.valormin = :valormin"),
    @NamedQuery(name = "Inmueble.findByValormax", query = "SELECT i FROM Inmueble i WHERE i.valormax = :valormax"),
    @NamedQuery(name = "Inmueble.findByDireccion", query = "SELECT i FROM Inmueble i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Inmueble.findByPadron", query = "SELECT i FROM Inmueble i WHERE i.padron = :padron"),
    @NamedQuery(name = "Inmueble.findByBanios", query = "SELECT i FROM Inmueble i WHERE i.banios = :banios"),
    @NamedQuery(name = "Inmueble.findByHabitaciones", query = "SELECT i FROM Inmueble i WHERE i.habitaciones = :habitaciones"),
    @NamedQuery(name = "Inmueble.findByPisos", query = "SELECT i FROM Inmueble i WHERE i.pisos = :pisos"),
    @NamedQuery(name = "Inmueble.findByGarage", query = "SELECT i FROM Inmueble i WHERE i.garage = :garage"),
    @NamedQuery(name = "Inmueble.findByJardin", query = "SELECT i FROM Inmueble i WHERE i.jardin = :jardin"),
    @NamedQuery(name = "Inmueble.findByDescripcion", query = "SELECT i FROM Inmueble i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Inmueble.findByTitulo", query = "SELECT i FROM Inmueble i WHERE i.titulo = :titulo")})
public class Inmueble implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "gid_inm")
    private Integer gidInm;
    @Size(max = 100)
    @Column(name = "proposito")
    private String proposito;
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;
    @Column(name = "tipo")
    private Integer tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valormin")
    private Double valormin;
    @Column(name = "valormax")
    private Double valormax;
    @Size(max = 2000)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "padron")
    private Integer padron;
    @Column(name = "banios")
    private Integer banios;
    @Column(name = "habitaciones")
    private Integer habitaciones;
    @Column(name = "pisos")
    private Integer pisos;
    @Column(name = "garage")
    private Boolean garage;
    @Column(name = "jardin")
    private Boolean jardin;
    @Size(max = 2000)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 200)
    @Column(name = "titulo")
    private String titulo;
    @OneToMany(mappedBy = "gidInm")
    private Collection<Imagenes> imagenesCollection;
    @OneToMany(mappedBy = "gidInm")
    private Collection<Consulta> consultaCollection;
    @JoinColumn(name = "gidzona", referencedColumnName = "gidzona")
    
  
    @ManyToOne
    private Zonas gidzona;
    @JoinColumn(name = "id_propietario", referencedColumnName = "id_propietario")
    @ManyToOne
    private Propietario idPropietario;
    @JoinColumn(name = "id_admin", referencedColumnName = "id_admin")
    @ManyToOne
    private Administrador idAdmin;

    public Inmueble() {
    }

    public Inmueble(int gidInm, String proposito, String estado, int tipo, double valormin, double valormax, String direccion,int padron, Object idPropietario, int banios, int habitaciones, int pisos, boolean garage, boolean jardin,String descripcion,String titulo, Object gidzona, Object idAdmin ){
        
        this.gidInm = gidInm;
        this.proposito = proposito;
        this.estado = estado;
        this.tipo = tipo;
        this.valormin = valormin;
        this.valormax = valormax;
        this.direccion = direccion;
        this.padron = padron;
        this.idPropietario = (Propietario) idPropietario;
        this.banios = banios;
        this.habitaciones = habitaciones;
        this.pisos = pisos;
        this.garage = garage;
        this.jardin = jardin;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.gidzona = (Zonas) gidzona;
        this.idAdmin = (Administrador) idAdmin;                
    }
    
    
    public Inmueble(Integer gidInm) {
        this.gidInm = gidInm;
    }

    
    public Integer getGidInm() {
        return gidInm;
    }

    public void setGidInm(Integer gidInm) {
        this.gidInm = gidInm;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getValormin() {
        return valormin;
    }

    public void setValormin(Double valormin) {
        this.valormin = valormin;
    }

    public Double getValormax() {
        return valormax;
    }

    public void setValormax(Double valormax) {
        this.valormax = valormax;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getPadron() {
        return padron;
    }

    public void setPadron(Integer padron) {
        this.padron = padron;
    }

    public Integer getBanios() {
        return banios;
    }

    public void setBanios(Integer banios) {
        this.banios = banios;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getPisos() {
        return pisos;
    }

    public void setPisos(Integer pisos) {
        this.pisos = pisos;
    }

    public Boolean getGarage() {
        return garage;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Boolean getJardin() {
        return jardin;
    }

    public void setJardin(Boolean jardin) {
        this.jardin = jardin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public Collection<Imagenes> getImagenesCollection() {
        return imagenesCollection;
    }

    public void setImagenesCollection(Collection<Imagenes> imagenesCollection) {
        this.imagenesCollection = imagenesCollection;
    }

    @XmlTransient
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    public Zonas getGidzona() {
        return gidzona;
    }

    public void setGidzona(Zonas gidzona) {
        this.gidzona = gidzona;
    }

    public Propietario getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Propietario idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Administrador getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Administrador idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gidInm != null ? gidInm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inmueble)) {
            return false;
        }
        Inmueble other = (Inmueble) object;
        if ((this.gidInm == null && other.gidInm != null) || (this.gidInm != null && !this.gidInm.equals(other.gidInm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Inmueble[ gidInm=" + gidInm + " ]";
    }

    private int setGidInm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}