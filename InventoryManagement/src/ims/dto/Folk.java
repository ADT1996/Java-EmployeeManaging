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
@Table(name = "folk")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Folk.findAll", query = "SELECT f FROM Folk f")
    , @NamedQuery(name = "Folk.findById", query = "SELECT f FROM Folk f WHERE f.id = :id")
    , @NamedQuery(name = "Folk.findByFolk", query = "SELECT f FROM Folk f WHERE f.folk = :folk")})
public class Folk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Folk")
    private String folk;
    @OneToMany(mappedBy = "folk")
    private Collection<Employee> employeeCollection;

    public Folk() {
    }

    public Folk(Integer id) {
        this.id = id;
    }

    public Folk(Integer id, String folk) {
        this.id = id;
        this.folk = folk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
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
        if (!(object instanceof Folk)) {
            return false;
        }
        Folk other = (Folk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Folk[ id=" + id + " ]";
    }
    
}
