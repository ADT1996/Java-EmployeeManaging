/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "religion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Religion.findAll", query = "SELECT r FROM Religion r")
    , @NamedQuery(name = "Religion.findById", query = "SELECT r FROM Religion r WHERE r.id = :id")
    , @NamedQuery(name = "Religion.findByReligion", query = "SELECT r FROM Religion r WHERE r.religion = :religion")})
public class Religion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Religion")
    private String religion;
    @OneToMany(mappedBy = "religion")
    private Collection<Employee> employeeCollection;

    public Religion() {
    }

    public Religion(Integer id) {
        this.id = id;
    }

    public Religion(Integer id, String religion) {
        this.id = id;
        this.religion = religion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Religion)) {
            return false;
        }
        Religion other = (Religion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Religion[ id=" + id + " ]";
    }
    
}
