/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abc
 */
@Entity
@Table(name = "employee", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Email"})
    , @UniqueConstraint(columnNames = {"MobieNumber"})
    , @UniqueConstraint(columnNames = {"PersonCode"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, length = 15)
    private String id;
    @Basic(optional = false)
    @Column(name = "FullName", nullable = false, length = 50)
    private String fullName;
    @Column(name = "NickName", length = 50)
    private String nickName;
    @Basic(optional = false)
    @Column(name = "Gender", nullable = false)
    private boolean gender;
    @Basic(optional = false)
    @Column(name = "Marries", nullable = false)
    private boolean marries;
    @Column(name = "MobieNumber", length = 11)
    private String mobieNumber;
    @Column(name = "Phone", length = 8)
    private String phone;
    @Column(name = "Email", length = 50)
    private String email;
    @Column(name = "BirthDay")
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    @Column(name = "BirthPlace", length = 50)
    private String birthPlace;
    @Column(name = "PersonCode", length = 20)
    private String personCode;
    @Column(name = "TakenPCDate")
    @Temporal(TemporalType.DATE)
    private Date takenPCDate;
    @Column(name = "TakenPCPlace", length = 50)
    private String takenPCPlace;
    @Column(name = "NativeLand", length = 50)
    private String nativeLand;
    @Column(name = "Address", length = 50)
    private String address;
    @Column(name = "Tabernacle", length = 50)
    private String tabernacle;
    @Basic(optional = false)
    @Column(name = "StartDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "BaseSalary", nullable = false)
    private long baseSalary;
    @Basic(optional = false)
    @Column(name = "FactorSalary", nullable = false)
    private double factorSalary;
    @Basic(optional = false)
    @Column(name = "Salary", nullable = false)
    private long salary;
    @Basic(optional = false)
    @Column(name = "AllowedSalary", nullable = false)
    private long allowedSalary;
    @Column(name = "LaborCode")
    private Integer laborCode;
    @Column(name = "TakenLaborPlace", length = 50)
    private String takenLaborPlace;
    @Column(name = "TakenLaborDate")
    @Temporal(TemporalType.DATE)
    private Date takenLaborDate;
    @Column(name = "BankId", length = 50)
    private String bankId;
    @Column(name = "Bank", length = 50)
    private String bank;
    @JoinColumn(name = "Computing", referencedColumnName = "id")
    @ManyToOne
    private Computing computing;
    @JoinColumn(name = "City", referencedColumnName = "idcity")
    @ManyToOne
    private City city;
    @JoinColumn(name = "Degree", referencedColumnName = "id")
    @ManyToOne
    private Degree degree;
    @JoinColumn(name = "Deparment", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Deparment deparment;
    @JoinColumn(name = "Position", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EmployeePosition position;
    @JoinColumn(name = "Folk", referencedColumnName = "id")
    @ManyToOne
    private Folk folk;
    @JoinColumn(name = "ForeignLanguage", referencedColumnName = "id")
    @ManyToOne
    private Foreignlanguage foreignLanguage;
    @JoinColumn(name = "TypeStaff", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Typestaff typeStaff;
    @JoinColumn(name = "Job", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Job job;
    @JoinColumn(name = "Learning", referencedColumnName = "id")
    @ManyToOne
    private Learning learning;
    @JoinColumn(name = "Nationality", referencedColumnName = "id")
    @ManyToOne
    private Nationality nationality;
    @JoinColumn(name = "Religion", referencedColumnName = "id")
    @ManyToOne
    private Religion religion;

    public Employee() {
    }

    public Employee(String id) {
        this.id = id;
    }

    public Employee(String id, String fullName, boolean gender, boolean marries, Date startDate, long baseSalary, double factorSalary, long salary, long allowedSalary) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.marries = marries;
        this.startDate = startDate;
        this.baseSalary = baseSalary;
        this.factorSalary = factorSalary;
        this.salary = salary;
        this.allowedSalary = allowedSalary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean getMarries() {
        return marries;
    }

    public void setMarries(boolean marries) {
        this.marries = marries;
    }

    public String getMobieNumber() {
        return mobieNumber;
    }

    public void setMobieNumber(String mobieNumber) {
        this.mobieNumber = mobieNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public Date getTakenPCDate() {
        return takenPCDate;
    }

    public void setTakenPCDate(Date takenPCDate) {
        this.takenPCDate = takenPCDate;
    }

    public String getTakenPCPlace() {
        return takenPCPlace;
    }

    public void setTakenPCPlace(String takenPCPlace) {
        this.takenPCPlace = takenPCPlace;
    }

    public String getNativeLand() {
        return nativeLand;
    }

    public void setNativeLand(String nativeLand) {
        this.nativeLand = nativeLand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTabernacle() {
        return tabernacle;
    }

    public void setTabernacle(String tabernacle) {
        this.tabernacle = tabernacle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public long getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(long baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getFactorSalary() {
        return factorSalary;
    }

    public void setFactorSalary(double factorSalary) {
        this.factorSalary = factorSalary;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public long getAllowedSalary() {
        return allowedSalary;
    }

    public void setAllowedSalary(long allowedSalary) {
        this.allowedSalary = allowedSalary;
    }

    public Integer getLaborCode() {
        return laborCode;
    }

    public void setLaborCode(Integer laborCode) {
        this.laborCode = laborCode;
    }

    public String getTakenLaborPlace() {
        return takenLaborPlace;
    }

    public void setTakenLaborPlace(String takenLaborPlace) {
        this.takenLaborPlace = takenLaborPlace;
    }

    public Date getTakenLaborDate() {
        return takenLaborDate;
    }

    public void setTakenLaborDate(Date takenLaborDate) {
        this.takenLaborDate = takenLaborDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Computing getComputing() {
        return computing;
    }

    public void setComputing(Computing computing) {
        this.computing = computing;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Deparment getDeparment() {
        return deparment;
    }

    public void setDeparment(Deparment deparment) {
        this.deparment = deparment;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public Folk getFolk() {
        return folk;
    }

    public void setFolk(Folk folk) {
        this.folk = folk;
    }

    public Foreignlanguage getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(Foreignlanguage foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public Typestaff getTypeStaff() {
        return typeStaff;
    }

    public void setTypeStaff(Typestaff typeStaff) {
        this.typeStaff = typeStaff;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Learning getLearning() {
        return learning;
    }

    public void setLearning(Learning learning) {
        this.learning = learning;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Employee[ id=" + id + " ]";
    }
    
}
