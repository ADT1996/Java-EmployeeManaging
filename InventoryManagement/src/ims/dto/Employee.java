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

/**
 *
 * @author abc
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "publicKey")
    private String publicKey;
    @Basic(optional = false)
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "NickName")
    private String nickName;
    @Basic(optional = false)
    @Column(name = "Gender")
    private boolean gender;
    @Basic(optional = false)
    @Column(name = "Married")
    private boolean married;
    @Column(name = "MobieNumber")
    private Long mobieNumber;
    @Column(name = "PhoneHome")
    private Integer phoneHome;
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "BirthDay")
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    @Basic(optional = false)
    @Column(name = "BirthPlace")
    private String birthPlace;
    @Basic(optional = false)
    @Column(name = "PersonCode")
    private long personCode;
    @Basic(optional = false)
    @Column(name = "TakenCodeDay")
    @Temporal(TemporalType.DATE)
    private Date takenCodeDay;
    @Basic(optional = false)
    @Column(name = "TakenCodePlace")
    private String takenCodePlace;
    @Basic(optional = false)
    @Column(name = "NativeLand")
    private String nativeLand;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Column(name = "Tabernacle")
    private String tabernacle;
    @Basic(optional = false)
    @Column(name = "StartDay")
    @Temporal(TemporalType.DATE)
    private Date startDay;
    @Basic(optional = false)
    @Column(name = "BaseSalary")
    private int baseSalary;
    @Basic(optional = false)
    @Column(name = "SalaryCoefficient")
    private float salaryCoefficient;
    @Basic(optional = false)
    @Column(name = "Salary")
    private int salary;
    @Basic(optional = false)
    @Column(name = "SalaryAllowance")
    private int salaryAllowance;
    @Column(name = "NumberLabor")
    private Integer numberLabor;
    @Column(name = "TakenLaborDay")
    @Temporal(TemporalType.DATE)
    private Date takenLaborDay;
    @Column(name = "TakenLaborPlace")
    private String takenLaborPlace;
    @Column(name = "IdBank")
    private String idBank;
    @Column(name = "Bank")
    private String bank;
    @JoinColumn(name = "Folk", referencedColumnName = "id")
    @ManyToOne
    private Folk folk;
    @JoinColumn(name = "City", referencedColumnName = "idcity")
    @ManyToOne(optional = false)
    private City city;
    @JoinColumn(name = "Computing", referencedColumnName = "id")
    @ManyToOne
    private Computing computing;
    @JoinColumn(name = "Degree", referencedColumnName = "id")
    @ManyToOne
    private Degree degree;
    @JoinColumn(name = "TypeStaff", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Typestaff typeStaff;
    @JoinColumn(name = "Deparment", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Deparment deparment;
    @JoinColumn(name = "Position", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmployeePosition position;
    @JoinColumn(name = "ForeignLanguage", referencedColumnName = "id")
    @ManyToOne
    private Foreignlanguage foreignLanguage;
    @JoinColumn(name = "Job", referencedColumnName = "id")
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

    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(Integer id, String publicKey, String fullName, boolean gender, boolean married, Date birthDay, String birthPlace, long personCode, Date takenCodeDay, String takenCodePlace, String nativeLand, String address, Date startDay, int baseSalary, float salaryCoefficient, int salary, int salaryAllowance) {
        this.id = id;
        this.publicKey = publicKey;
        this.fullName = fullName;
        this.gender = gender;
        this.married = married;
        this.birthDay = birthDay;
        this.birthPlace = birthPlace;
        this.personCode = personCode;
        this.takenCodeDay = takenCodeDay;
        this.takenCodePlace = takenCodePlace;
        this.nativeLand = nativeLand;
        this.address = address;
        this.startDay = startDay;
        this.baseSalary = baseSalary;
        this.salaryCoefficient = salaryCoefficient;
        this.salary = salary;
        this.salaryAllowance = salaryAllowance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    public boolean getMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Long getMobieNumber() {
        return mobieNumber;
    }

    public void setMobieNumber(Long mobieNumber) {
        this.mobieNumber = mobieNumber;
    }

    public Integer getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(Integer phoneHome) {
        this.phoneHome = phoneHome;
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

    public long getPersonCode() {
        return personCode;
    }

    public void setPersonCode(long personCode) {
        this.personCode = personCode;
    }

    public Date getTakenCodeDay() {
        return takenCodeDay;
    }

    public void setTakenCodeDay(Date takenCodeDay) {
        this.takenCodeDay = takenCodeDay;
    }

    public String getTakenCodePlace() {
        return takenCodePlace;
    }

    public void setTakenCodePlace(String takenCodePlace) {
        this.takenCodePlace = takenCodePlace;
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

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public float getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(float salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalaryAllowance() {
        return salaryAllowance;
    }

    public void setSalaryAllowance(int salaryAllowance) {
        this.salaryAllowance = salaryAllowance;
    }

    public Integer getNumberLabor() {
        return numberLabor;
    }

    public void setNumberLabor(Integer numberLabor) {
        this.numberLabor = numberLabor;
    }

    public Date getTakenLaborDay() {
        return takenLaborDay;
    }

    public void setTakenLaborDay(Date takenLaborDay) {
        this.takenLaborDay = takenLaborDay;
    }

    public String getTakenLaborPlace() {
        return takenLaborPlace;
    }

    public void setTakenLaborPlace(String takenLaborPlace) {
        this.takenLaborPlace = takenLaborPlace;
    }

    public String getIdBank() {
        return idBank;
    }

    public void setIdBank(String idBank) {
        this.idBank = idBank;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Folk getFolk() {
        return folk;
    }

    public void setFolk(Folk folk) {
        this.folk = folk;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Computing getComputing() {
        return computing;
    }

    public void setComputing(Computing computing) {
        this.computing = computing;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Typestaff getTypeStaff() {
        return typeStaff;
    }

    public void setTypeStaff(Typestaff typeStaff) {
        this.typeStaff = typeStaff;
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

    public Foreignlanguage getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(Foreignlanguage foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
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
