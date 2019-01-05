/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.bll.exceptions.NonexistentEntityException;
import ims.bll.exceptions.PreexistingEntityException;
import ims.dto.City;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Employee;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class CityJpaController implements Serializable {

    public CityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(City city) throws PreexistingEntityException, Exception {
        if (city.getEmployeeCollection() == null) {
            city.setEmployeeCollection(new ArrayList<Employee>());
        }
        if (city.getEmployeeCollection1() == null) {
            city.setEmployeeCollection1(new ArrayList<Employee>());
        }
        if (city.getEmployeeCollection2() == null) {
            city.setEmployeeCollection2(new ArrayList<Employee>());
        }
        if (city.getEmployeeCollection3() == null) {
            city.setEmployeeCollection3(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : city.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            city.setEmployeeCollection(attachedEmployeeCollection);
            Collection<Employee> attachedEmployeeCollection1 = new ArrayList<Employee>();
            for (Employee employeeCollection1EmployeeToAttach : city.getEmployeeCollection1()) {
                employeeCollection1EmployeeToAttach = em.getReference(employeeCollection1EmployeeToAttach.getClass(), employeeCollection1EmployeeToAttach.getId());
                attachedEmployeeCollection1.add(employeeCollection1EmployeeToAttach);
            }
            city.setEmployeeCollection1(attachedEmployeeCollection1);
            Collection<Employee> attachedEmployeeCollection2 = new ArrayList<Employee>();
            for (Employee employeeCollection2EmployeeToAttach : city.getEmployeeCollection2()) {
                employeeCollection2EmployeeToAttach = em.getReference(employeeCollection2EmployeeToAttach.getClass(), employeeCollection2EmployeeToAttach.getId());
                attachedEmployeeCollection2.add(employeeCollection2EmployeeToAttach);
            }
            city.setEmployeeCollection2(attachedEmployeeCollection2);
            Collection<Employee> attachedEmployeeCollection3 = new ArrayList<Employee>();
            for (Employee employeeCollection3EmployeeToAttach : city.getEmployeeCollection3()) {
                employeeCollection3EmployeeToAttach = em.getReference(employeeCollection3EmployeeToAttach.getClass(), employeeCollection3EmployeeToAttach.getId());
                attachedEmployeeCollection3.add(employeeCollection3EmployeeToAttach);
            }
            city.setEmployeeCollection3(attachedEmployeeCollection3);
            em.persist(city);
            for (Employee employeeCollectionEmployee : city.getEmployeeCollection()) {
                City oldNativeLandOfEmployeeCollectionEmployee = employeeCollectionEmployee.getNativeLand();
                employeeCollectionEmployee.setNativeLand(city);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldNativeLandOfEmployeeCollectionEmployee != null) {
                    oldNativeLandOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldNativeLandOfEmployeeCollectionEmployee = em.merge(oldNativeLandOfEmployeeCollectionEmployee);
                }
            }
            for (Employee employeeCollection1Employee : city.getEmployeeCollection1()) {
                City oldTakenCodePlaceOfEmployeeCollection1Employee = employeeCollection1Employee.getTakenCodePlace();
                employeeCollection1Employee.setTakenCodePlace(city);
                employeeCollection1Employee = em.merge(employeeCollection1Employee);
                if (oldTakenCodePlaceOfEmployeeCollection1Employee != null) {
                    oldTakenCodePlaceOfEmployeeCollection1Employee.getEmployeeCollection1().remove(employeeCollection1Employee);
                    oldTakenCodePlaceOfEmployeeCollection1Employee = em.merge(oldTakenCodePlaceOfEmployeeCollection1Employee);
                }
            }
            for (Employee employeeCollection2Employee : city.getEmployeeCollection2()) {
                City oldTakenLaborPlaceOfEmployeeCollection2Employee = employeeCollection2Employee.getTakenLaborPlace();
                employeeCollection2Employee.setTakenLaborPlace(city);
                employeeCollection2Employee = em.merge(employeeCollection2Employee);
                if (oldTakenLaborPlaceOfEmployeeCollection2Employee != null) {
                    oldTakenLaborPlaceOfEmployeeCollection2Employee.getEmployeeCollection2().remove(employeeCollection2Employee);
                    oldTakenLaborPlaceOfEmployeeCollection2Employee = em.merge(oldTakenLaborPlaceOfEmployeeCollection2Employee);
                }
            }
            for (Employee employeeCollection3Employee : city.getEmployeeCollection3()) {
                City oldCityOfEmployeeCollection3Employee = employeeCollection3Employee.getCity();
                employeeCollection3Employee.setCity(city);
                employeeCollection3Employee = em.merge(employeeCollection3Employee);
                if (oldCityOfEmployeeCollection3Employee != null) {
                    oldCityOfEmployeeCollection3Employee.getEmployeeCollection3().remove(employeeCollection3Employee);
                    oldCityOfEmployeeCollection3Employee = em.merge(oldCityOfEmployeeCollection3Employee);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCity(city.getIdcity()) != null) {
                throw new PreexistingEntityException("City " + city + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(City city) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City persistentCity = em.find(City.class, city.getIdcity());
            Collection<Employee> employeeCollectionOld = persistentCity.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = city.getEmployeeCollection();
            Collection<Employee> employeeCollection1Old = persistentCity.getEmployeeCollection1();
            Collection<Employee> employeeCollection1New = city.getEmployeeCollection1();
            Collection<Employee> employeeCollection2Old = persistentCity.getEmployeeCollection2();
            Collection<Employee> employeeCollection2New = city.getEmployeeCollection2();
            Collection<Employee> employeeCollection3Old = persistentCity.getEmployeeCollection3();
            Collection<Employee> employeeCollection3New = city.getEmployeeCollection3();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            city.setEmployeeCollection(employeeCollectionNew);
            Collection<Employee> attachedEmployeeCollection1New = new ArrayList<Employee>();
            for (Employee employeeCollection1NewEmployeeToAttach : employeeCollection1New) {
                employeeCollection1NewEmployeeToAttach = em.getReference(employeeCollection1NewEmployeeToAttach.getClass(), employeeCollection1NewEmployeeToAttach.getId());
                attachedEmployeeCollection1New.add(employeeCollection1NewEmployeeToAttach);
            }
            employeeCollection1New = attachedEmployeeCollection1New;
            city.setEmployeeCollection1(employeeCollection1New);
            Collection<Employee> attachedEmployeeCollection2New = new ArrayList<Employee>();
            for (Employee employeeCollection2NewEmployeeToAttach : employeeCollection2New) {
                employeeCollection2NewEmployeeToAttach = em.getReference(employeeCollection2NewEmployeeToAttach.getClass(), employeeCollection2NewEmployeeToAttach.getId());
                attachedEmployeeCollection2New.add(employeeCollection2NewEmployeeToAttach);
            }
            employeeCollection2New = attachedEmployeeCollection2New;
            city.setEmployeeCollection2(employeeCollection2New);
            Collection<Employee> attachedEmployeeCollection3New = new ArrayList<Employee>();
            for (Employee employeeCollection3NewEmployeeToAttach : employeeCollection3New) {
                employeeCollection3NewEmployeeToAttach = em.getReference(employeeCollection3NewEmployeeToAttach.getClass(), employeeCollection3NewEmployeeToAttach.getId());
                attachedEmployeeCollection3New.add(employeeCollection3NewEmployeeToAttach);
            }
            employeeCollection3New = attachedEmployeeCollection3New;
            city.setEmployeeCollection3(employeeCollection3New);
            city = em.merge(city);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setNativeLand(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    City oldNativeLandOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getNativeLand();
                    employeeCollectionNewEmployee.setNativeLand(city);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldNativeLandOfEmployeeCollectionNewEmployee != null && !oldNativeLandOfEmployeeCollectionNewEmployee.equals(city)) {
                        oldNativeLandOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldNativeLandOfEmployeeCollectionNewEmployee = em.merge(oldNativeLandOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            for (Employee employeeCollection1OldEmployee : employeeCollection1Old) {
                if (!employeeCollection1New.contains(employeeCollection1OldEmployee)) {
                    employeeCollection1OldEmployee.setTakenCodePlace(null);
                    employeeCollection1OldEmployee = em.merge(employeeCollection1OldEmployee);
                }
            }
            for (Employee employeeCollection1NewEmployee : employeeCollection1New) {
                if (!employeeCollection1Old.contains(employeeCollection1NewEmployee)) {
                    City oldTakenCodePlaceOfEmployeeCollection1NewEmployee = employeeCollection1NewEmployee.getTakenCodePlace();
                    employeeCollection1NewEmployee.setTakenCodePlace(city);
                    employeeCollection1NewEmployee = em.merge(employeeCollection1NewEmployee);
                    if (oldTakenCodePlaceOfEmployeeCollection1NewEmployee != null && !oldTakenCodePlaceOfEmployeeCollection1NewEmployee.equals(city)) {
                        oldTakenCodePlaceOfEmployeeCollection1NewEmployee.getEmployeeCollection1().remove(employeeCollection1NewEmployee);
                        oldTakenCodePlaceOfEmployeeCollection1NewEmployee = em.merge(oldTakenCodePlaceOfEmployeeCollection1NewEmployee);
                    }
                }
            }
            for (Employee employeeCollection2OldEmployee : employeeCollection2Old) {
                if (!employeeCollection2New.contains(employeeCollection2OldEmployee)) {
                    employeeCollection2OldEmployee.setTakenLaborPlace(null);
                    employeeCollection2OldEmployee = em.merge(employeeCollection2OldEmployee);
                }
            }
            for (Employee employeeCollection2NewEmployee : employeeCollection2New) {
                if (!employeeCollection2Old.contains(employeeCollection2NewEmployee)) {
                    City oldTakenLaborPlaceOfEmployeeCollection2NewEmployee = employeeCollection2NewEmployee.getTakenLaborPlace();
                    employeeCollection2NewEmployee.setTakenLaborPlace(city);
                    employeeCollection2NewEmployee = em.merge(employeeCollection2NewEmployee);
                    if (oldTakenLaborPlaceOfEmployeeCollection2NewEmployee != null && !oldTakenLaborPlaceOfEmployeeCollection2NewEmployee.equals(city)) {
                        oldTakenLaborPlaceOfEmployeeCollection2NewEmployee.getEmployeeCollection2().remove(employeeCollection2NewEmployee);
                        oldTakenLaborPlaceOfEmployeeCollection2NewEmployee = em.merge(oldTakenLaborPlaceOfEmployeeCollection2NewEmployee);
                    }
                }
            }
            for (Employee employeeCollection3OldEmployee : employeeCollection3Old) {
                if (!employeeCollection3New.contains(employeeCollection3OldEmployee)) {
                    employeeCollection3OldEmployee.setCity(null);
                    employeeCollection3OldEmployee = em.merge(employeeCollection3OldEmployee);
                }
            }
            for (Employee employeeCollection3NewEmployee : employeeCollection3New) {
                if (!employeeCollection3Old.contains(employeeCollection3NewEmployee)) {
                    City oldCityOfEmployeeCollection3NewEmployee = employeeCollection3NewEmployee.getCity();
                    employeeCollection3NewEmployee.setCity(city);
                    employeeCollection3NewEmployee = em.merge(employeeCollection3NewEmployee);
                    if (oldCityOfEmployeeCollection3NewEmployee != null && !oldCityOfEmployeeCollection3NewEmployee.equals(city)) {
                        oldCityOfEmployeeCollection3NewEmployee.getEmployeeCollection3().remove(employeeCollection3NewEmployee);
                        oldCityOfEmployeeCollection3NewEmployee = em.merge(oldCityOfEmployeeCollection3NewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = city.getIdcity();
                if (findCity(id) == null) {
                    throw new NonexistentEntityException("The city with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City city;
            try {
                city = em.getReference(City.class, id);
                city.getIdcity();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The city with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = city.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setNativeLand(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            Collection<Employee> employeeCollection1 = city.getEmployeeCollection1();
            for (Employee employeeCollection1Employee : employeeCollection1) {
                employeeCollection1Employee.setTakenCodePlace(null);
                employeeCollection1Employee = em.merge(employeeCollection1Employee);
            }
            Collection<Employee> employeeCollection2 = city.getEmployeeCollection2();
            for (Employee employeeCollection2Employee : employeeCollection2) {
                employeeCollection2Employee.setTakenLaborPlace(null);
                employeeCollection2Employee = em.merge(employeeCollection2Employee);
            }
            Collection<Employee> employeeCollection3 = city.getEmployeeCollection3();
            for (Employee employeeCollection3Employee : employeeCollection3) {
                employeeCollection3Employee.setCity(null);
                employeeCollection3Employee = em.merge(employeeCollection3Employee);
            }
            em.remove(city);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<City> findCityEntities() {
        return findCityEntities(true, -1, -1);
    }

    public List<City> findCityEntities(int maxResults, int firstResult) {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(City.class));
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

    public City findCity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(City.class, id);
        } finally {
            em.close();
        }
    }

    public int getCityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<City> rt = cq.from(City.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
