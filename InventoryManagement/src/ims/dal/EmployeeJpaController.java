/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.NonexistentEntityException;
import ims.dal.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.City;
import ims.dto.Computing;
import ims.dto.Degree;
import ims.dto.Deparment;
import ims.dto.Employee;
import ims.dto.EmployeePosition;
import ims.dto.Folk;
import ims.dto.Foreignlanguage;
import ims.dto.Job;
import ims.dto.Learning;
import ims.dto.Nationality;
import ims.dto.Religion;
import ims.dto.Typestaff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City city = employee.getCity();
            if (city != null) {
                city = em.getReference(city.getClass(), city.getIdcity());
                employee.setCity(city);
            }
            Computing computing = employee.getComputing();
            if (computing != null) {
                computing = em.getReference(computing.getClass(), computing.getId());
                employee.setComputing(computing);
            }
            Degree degree = employee.getDegree();
            if (degree != null) {
                degree = em.getReference(degree.getClass(), degree.getId());
                employee.setDegree(degree);
            }
            Deparment deparment = employee.getDeparment();
            if (deparment != null) {
                deparment = em.getReference(deparment.getClass(), deparment.getId());
                employee.setDeparment(deparment);
            }
            EmployeePosition position = employee.getPosition();
            if (position != null) {
                position = em.getReference(position.getClass(), position.getId());
                employee.setPosition(position);
            }
            Folk folk = employee.getFolk();
            if (folk != null) {
                folk = em.getReference(folk.getClass(), folk.getId());
                employee.setFolk(folk);
            }
            Foreignlanguage foreignLanguage = employee.getForeignLanguage();
            if (foreignLanguage != null) {
                foreignLanguage = em.getReference(foreignLanguage.getClass(), foreignLanguage.getId());
                employee.setForeignLanguage(foreignLanguage);
            }
            Job job = employee.getJob();
            if (job != null) {
                job = em.getReference(job.getClass(), job.getId());
                employee.setJob(job);
            }
            Learning learning = employee.getLearning();
            if (learning != null) {
                learning = em.getReference(learning.getClass(), learning.getId());
                employee.setLearning(learning);
            }
            Nationality nationality = employee.getNationality();
            if (nationality != null) {
                nationality = em.getReference(nationality.getClass(), nationality.getId());
                employee.setNationality(nationality);
            }
            Religion religion = employee.getReligion();
            if (religion != null) {
                religion = em.getReference(religion.getClass(), religion.getId());
                employee.setReligion(religion);
            }
            Typestaff typeStaff = employee.getTypeStaff();
            if (typeStaff != null) {
                typeStaff = em.getReference(typeStaff.getClass(), typeStaff.getId());
                employee.setTypeStaff(typeStaff);
            }
            em.persist(employee);
            if (city != null) {
                city.getEmployeeCollection().add(employee);
                city = em.merge(city);
            }
            if (computing != null) {
                computing.getEmployeeCollection().add(employee);
                computing = em.merge(computing);
            }
            if (degree != null) {
                degree.getEmployeeCollection().add(employee);
                degree = em.merge(degree);
            }
            if (deparment != null) {
                deparment.getEmployeeCollection().add(employee);
                deparment = em.merge(deparment);
            }
            if (position != null) {
                position.getEmployeeCollection().add(employee);
                position = em.merge(position);
            }
            if (folk != null) {
                folk.getEmployeeCollection().add(employee);
                folk = em.merge(folk);
            }
            if (foreignLanguage != null) {
                foreignLanguage.getEmployeeCollection().add(employee);
                foreignLanguage = em.merge(foreignLanguage);
            }
            if (job != null) {
                job.getEmployeeCollection().add(employee);
                job = em.merge(job);
            }
            if (learning != null) {
                learning.getEmployeeCollection().add(employee);
                learning = em.merge(learning);
            }
            if (nationality != null) {
                nationality.getEmployeeCollection().add(employee);
                nationality = em.merge(nationality);
            }
            if (religion != null) {
                religion.getEmployeeCollection().add(employee);
                religion = em.merge(religion);
            }
            if (typeStaff != null) {
                typeStaff.getEmployeeCollection().add(employee);
                typeStaff = em.merge(typeStaff);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmployee(employee.getId()) != null) {
                throw new PreexistingEntityException("Employee " + employee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getId());
            City cityOld = persistentEmployee.getCity();
            City cityNew = employee.getCity();
            Computing computingOld = persistentEmployee.getComputing();
            Computing computingNew = employee.getComputing();
            Degree degreeOld = persistentEmployee.getDegree();
            Degree degreeNew = employee.getDegree();
            Deparment deparmentOld = persistentEmployee.getDeparment();
            Deparment deparmentNew = employee.getDeparment();
            EmployeePosition positionOld = persistentEmployee.getPosition();
            EmployeePosition positionNew = employee.getPosition();
            Folk folkOld = persistentEmployee.getFolk();
            Folk folkNew = employee.getFolk();
            Foreignlanguage foreignLanguageOld = persistentEmployee.getForeignLanguage();
            Foreignlanguage foreignLanguageNew = employee.getForeignLanguage();
            Job jobOld = persistentEmployee.getJob();
            Job jobNew = employee.getJob();
            Learning learningOld = persistentEmployee.getLearning();
            Learning learningNew = employee.getLearning();
            Nationality nationalityOld = persistentEmployee.getNationality();
            Nationality nationalityNew = employee.getNationality();
            Religion religionOld = persistentEmployee.getReligion();
            Religion religionNew = employee.getReligion();
            Typestaff typeStaffOld = persistentEmployee.getTypeStaff();
            Typestaff typeStaffNew = employee.getTypeStaff();
            if (cityNew != null) {
                cityNew = em.getReference(cityNew.getClass(), cityNew.getIdcity());
                employee.setCity(cityNew);
            }
            if (computingNew != null) {
                computingNew = em.getReference(computingNew.getClass(), computingNew.getId());
                employee.setComputing(computingNew);
            }
            if (degreeNew != null) {
                degreeNew = em.getReference(degreeNew.getClass(), degreeNew.getId());
                employee.setDegree(degreeNew);
            }
            if (deparmentNew != null) {
                deparmentNew = em.getReference(deparmentNew.getClass(), deparmentNew.getId());
                employee.setDeparment(deparmentNew);
            }
            if (positionNew != null) {
                positionNew = em.getReference(positionNew.getClass(), positionNew.getId());
                employee.setPosition(positionNew);
            }
            if (folkNew != null) {
                folkNew = em.getReference(folkNew.getClass(), folkNew.getId());
                employee.setFolk(folkNew);
            }
            if (foreignLanguageNew != null) {
                foreignLanguageNew = em.getReference(foreignLanguageNew.getClass(), foreignLanguageNew.getId());
                employee.setForeignLanguage(foreignLanguageNew);
            }
            if (jobNew != null) {
                jobNew = em.getReference(jobNew.getClass(), jobNew.getId());
                employee.setJob(jobNew);
            }
            if (learningNew != null) {
                learningNew = em.getReference(learningNew.getClass(), learningNew.getId());
                employee.setLearning(learningNew);
            }
            if (nationalityNew != null) {
                nationalityNew = em.getReference(nationalityNew.getClass(), nationalityNew.getId());
                employee.setNationality(nationalityNew);
            }
            if (religionNew != null) {
                religionNew = em.getReference(religionNew.getClass(), religionNew.getId());
                employee.setReligion(religionNew);
            }
            if (typeStaffNew != null) {
                typeStaffNew = em.getReference(typeStaffNew.getClass(), typeStaffNew.getId());
                employee.setTypeStaff(typeStaffNew);
            }
            employee = em.merge(employee);
            if (cityOld != null && !cityOld.equals(cityNew)) {
                cityOld.getEmployeeCollection().remove(employee);
                cityOld = em.merge(cityOld);
            }
            if (cityNew != null && !cityNew.equals(cityOld)) {
                cityNew.getEmployeeCollection().add(employee);
                cityNew = em.merge(cityNew);
            }
            if (computingOld != null && !computingOld.equals(computingNew)) {
                computingOld.getEmployeeCollection().remove(employee);
                computingOld = em.merge(computingOld);
            }
            if (computingNew != null && !computingNew.equals(computingOld)) {
                computingNew.getEmployeeCollection().add(employee);
                computingNew = em.merge(computingNew);
            }
            if (degreeOld != null && !degreeOld.equals(degreeNew)) {
                degreeOld.getEmployeeCollection().remove(employee);
                degreeOld = em.merge(degreeOld);
            }
            if (degreeNew != null && !degreeNew.equals(degreeOld)) {
                degreeNew.getEmployeeCollection().add(employee);
                degreeNew = em.merge(degreeNew);
            }
            if (deparmentOld != null && !deparmentOld.equals(deparmentNew)) {
                deparmentOld.getEmployeeCollection().remove(employee);
                deparmentOld = em.merge(deparmentOld);
            }
            if (deparmentNew != null && !deparmentNew.equals(deparmentOld)) {
                deparmentNew.getEmployeeCollection().add(employee);
                deparmentNew = em.merge(deparmentNew);
            }
            if (positionOld != null && !positionOld.equals(positionNew)) {
                positionOld.getEmployeeCollection().remove(employee);
                positionOld = em.merge(positionOld);
            }
            if (positionNew != null && !positionNew.equals(positionOld)) {
                positionNew.getEmployeeCollection().add(employee);
                positionNew = em.merge(positionNew);
            }
            if (folkOld != null && !folkOld.equals(folkNew)) {
                folkOld.getEmployeeCollection().remove(employee);
                folkOld = em.merge(folkOld);
            }
            if (folkNew != null && !folkNew.equals(folkOld)) {
                folkNew.getEmployeeCollection().add(employee);
                folkNew = em.merge(folkNew);
            }
            if (foreignLanguageOld != null && !foreignLanguageOld.equals(foreignLanguageNew)) {
                foreignLanguageOld.getEmployeeCollection().remove(employee);
                foreignLanguageOld = em.merge(foreignLanguageOld);
            }
            if (foreignLanguageNew != null && !foreignLanguageNew.equals(foreignLanguageOld)) {
                foreignLanguageNew.getEmployeeCollection().add(employee);
                foreignLanguageNew = em.merge(foreignLanguageNew);
            }
            if (jobOld != null && !jobOld.equals(jobNew)) {
                jobOld.getEmployeeCollection().remove(employee);
                jobOld = em.merge(jobOld);
            }
            if (jobNew != null && !jobNew.equals(jobOld)) {
                jobNew.getEmployeeCollection().add(employee);
                jobNew = em.merge(jobNew);
            }
            if (learningOld != null && !learningOld.equals(learningNew)) {
                learningOld.getEmployeeCollection().remove(employee);
                learningOld = em.merge(learningOld);
            }
            if (learningNew != null && !learningNew.equals(learningOld)) {
                learningNew.getEmployeeCollection().add(employee);
                learningNew = em.merge(learningNew);
            }
            if (nationalityOld != null && !nationalityOld.equals(nationalityNew)) {
                nationalityOld.getEmployeeCollection().remove(employee);
                nationalityOld = em.merge(nationalityOld);
            }
            if (nationalityNew != null && !nationalityNew.equals(nationalityOld)) {
                nationalityNew.getEmployeeCollection().add(employee);
                nationalityNew = em.merge(nationalityNew);
            }
            if (religionOld != null && !religionOld.equals(religionNew)) {
                religionOld.getEmployeeCollection().remove(employee);
                religionOld = em.merge(religionOld);
            }
            if (religionNew != null && !religionNew.equals(religionOld)) {
                religionNew.getEmployeeCollection().add(employee);
                religionNew = em.merge(religionNew);
            }
            if (typeStaffOld != null && !typeStaffOld.equals(typeStaffNew)) {
                typeStaffOld.getEmployeeCollection().remove(employee);
                typeStaffOld = em.merge(typeStaffOld);
            }
            if (typeStaffNew != null && !typeStaffNew.equals(typeStaffOld)) {
                typeStaffNew.getEmployeeCollection().add(employee);
                typeStaffNew = em.merge(typeStaffNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            City city = employee.getCity();
            if (city != null) {
                city.getEmployeeCollection().remove(employee);
                city = em.merge(city);
            }
            Computing computing = employee.getComputing();
            if (computing != null) {
                computing.getEmployeeCollection().remove(employee);
                computing = em.merge(computing);
            }
            Degree degree = employee.getDegree();
            if (degree != null) {
                degree.getEmployeeCollection().remove(employee);
                degree = em.merge(degree);
            }
            Deparment deparment = employee.getDeparment();
            if (deparment != null) {
                deparment.getEmployeeCollection().remove(employee);
                deparment = em.merge(deparment);
            }
            EmployeePosition position = employee.getPosition();
            if (position != null) {
                position.getEmployeeCollection().remove(employee);
                position = em.merge(position);
            }
            Folk folk = employee.getFolk();
            if (folk != null) {
                folk.getEmployeeCollection().remove(employee);
                folk = em.merge(folk);
            }
            Foreignlanguage foreignLanguage = employee.getForeignLanguage();
            if (foreignLanguage != null) {
                foreignLanguage.getEmployeeCollection().remove(employee);
                foreignLanguage = em.merge(foreignLanguage);
            }
            Job job = employee.getJob();
            if (job != null) {
                job.getEmployeeCollection().remove(employee);
                job = em.merge(job);
            }
            Learning learning = employee.getLearning();
            if (learning != null) {
                learning.getEmployeeCollection().remove(employee);
                learning = em.merge(learning);
            }
            Nationality nationality = employee.getNationality();
            if (nationality != null) {
                nationality.getEmployeeCollection().remove(employee);
                nationality = em.merge(nationality);
            }
            Religion religion = employee.getReligion();
            if (religion != null) {
                religion.getEmployeeCollection().remove(employee);
                religion = em.merge(religion);
            }
            Typestaff typeStaff = employee.getTypeStaff();
            if (typeStaff != null) {
                typeStaff.getEmployeeCollection().remove(employee);
                typeStaff = em.merge(typeStaff);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Employee findEmployee(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
