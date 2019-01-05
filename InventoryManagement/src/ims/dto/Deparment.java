/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "deparment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deparment.findAll", query = "SELECT d FROM Deparment d")
    , @NamedQuery(name = "Deparment.findById", query = "SELECT d FROM Deparment d WHERE d.id = :id")
    , @NamedQuery(name = "Deparment.findByDeparment", query = "SELECT d FROM Deparment d WHERE d.deparment = :deparment")})
public class Deparment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Deparment")
    private String deparment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deparment")
    private Collection<Employee> employeeCollection;

    public Deparment() {
    }

    public Deparment(Integer id) {
        this.id = id;
    }

    public Deparment(Integer id, String deparment) {
        this.id = id;
        this.deparment = deparment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeparment() {
        return deparment;
    }

    public void setDeparment(String deparment) {
        this.deparment = deparment;
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
        if (!(object instanceof Deparment)) {
            return false;
        }
        Deparment other = (Deparment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Deparment[ id=" + id + " ]";
    }
    
}
