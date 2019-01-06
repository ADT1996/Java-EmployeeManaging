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
@Table(name = "typestaff")
public class Typestaff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TypeStaff")
    private String typeStaff;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeStaff")
    private Collection<Employee> employeeCollection;

    public Typestaff() {
    }

    public Typestaff(Integer id) {
        this.id = id;
    }

    public Typestaff(Integer id, String typeStaff) {
        this.id = id;
        this.typeStaff = typeStaff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeStaff() {
        return typeStaff;
    }

    public void setTypeStaff(String typeStaff) {
        this.typeStaff = typeStaff;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typestaff)) {
            return false;
        }
        Typestaff other = (Typestaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Typestaff[ id=" + id + " ]";
    }
    
}
