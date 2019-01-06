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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcity")
    private Integer idcity;
    @Basic(optional = false)
    @Column(name = "City")
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private Collection<Employee> employeeCollection;

    public City() {
    }

    public City(Integer idcity) {
        this.idcity = idcity;
    }

    public City(Integer idcity, String city) {
        this.idcity = idcity;
        this.city = city;
    }

    public Integer getIdcity() {
        return idcity;
    }

    public void setIdcity(Integer idcity) {
        this.idcity = idcity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcity != null ? idcity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.idcity == null && other.idcity != null) || (this.idcity != null && !this.idcity.equals(other.idcity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.City[ idcity=" + idcity + " ]";
    }
    
}
