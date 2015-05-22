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
@Table(name = "consulta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c"),
    @NamedQuery(name = "Consulta.findByIdConsulta", query = "SELECT c FROM Consulta c WHERE c.idConsulta = :idConsulta"),
    @NamedQuery(name = "Consulta.findByNombre", query = "SELECT c FROM Consulta c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Consulta.findByApellido", query = "SELECT c FROM Consulta c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Consulta.findByTelefono", query = "SELECT c FROM Consulta c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Consulta.findByEmail", query = "SELECT c FROM Consulta c WHERE c.email = :email"),
    @NamedQuery(name = "Consulta.findByDescripcion", query = "SELECT c FROM Consulta c WHERE c.descripcion = :descripcion")})
public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_consulta")
    private Integer idConsulta;
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 30)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private Integer telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "gid_inm", referencedColumnName = "gid_inm")
    @ManyToOne
    private Inmueble gidInm;

    public Consulta() {
    }

    public Consulta(String nombre, String apellido, int telefono, String email, String descripcion, Object gidInm ){
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setDescripcion(descripcion);
        this.setGidInm((Inmueble) gidInm);
    }
    
    public Consulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idConsulta != null ? idConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.idConsulta == null && other.idConsulta != null) || (this.idConsulta != null && !this.idConsulta.equals(other.idConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Consulta[ idConsulta=" + idConsulta + " ]";
    }
    
}
