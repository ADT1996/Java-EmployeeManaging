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
@Table(name = "city")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    , @NamedQuery(name = "City.findByIdcity", query = "SELECT c FROM City c WHERE c.idcity = :idcity")
    , @NamedQuery(name = "City.findByCity", query = "SELECT c FROM City c WHERE c.city = :city")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcity")
    private Integer idcity;
    @Basic(optional = false)
    @Column(name = "City")
    private String city;
    @OneToMany(mappedBy = "nativeLand")
    private Collection<Employee> employeeCollection;
    @OneToMany(mappedBy = "takenCodePlace")
    private Collection<Employee> employeeCollection1;
    @OneToMany(mappedBy = "takenLaborPlace")
    private Collection<Employee> employeeCollection2;
    @OneToMany(mappedBy = "city")
    private Collection<Employee> employeeCollection3;

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

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection1() {
        return employeeCollection1;
    }

    public void setEmployeeCollection1(Collection<Employee> employeeCollection1) {
        this.employeeCollection1 = employeeCollection1;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection2() {
        return employeeCollection2;
    }

    public void setEmployeeCollection2(Collection<Employee> employeeCollection2) {
        this.employeeCollection2 = employeeCollection2;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection3() {
        return employeeCollection3;
    }

    public void setEmployeeCollection3(Collection<Employee> employeeCollection3) {
        this.employeeCollection3 = employeeCollection3;
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
