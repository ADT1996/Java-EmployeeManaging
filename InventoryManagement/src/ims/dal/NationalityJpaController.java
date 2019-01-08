/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Employee;
import ims.dto.Nationality;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class NationalityJpaController implements Serializable {

    public NationalityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nationality nationality) {
        if (nationality.getEmployeeCollection() == null) {
            nationality.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : nationality.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            nationality.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(nationality);
            for (Employee employeeCollectionEmployee : nationality.getEmployeeCollection()) {
                Nationality oldNationalityOfEmployeeCollectionEmployee = employeeCollectionEmployee.getNationality();
                employeeCollectionEmployee.setNationality(nationality);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldNationalityOfEmployeeCollectionEmployee != null) {
                    oldNationalityOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldNationalityOfEmployeeCollectionEmployee = em.merge(oldNationalityOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nationality nationality) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nationality persistentNationality = em.find(Nationality.class, nationality.getId());
            Collection<Employee> employeeCollectionOld = persistentNationality.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = nationality.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            nationality.setEmployeeCollection(employeeCollectionNew);
            nationality = em.merge(nationality);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setNationality(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Nationality oldNationalityOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getNationality();
                    employeeCollectionNewEmployee.setNationality(nationality);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldNationalityOfEmployeeCollectionNewEmployee != null && !oldNationalityOfEmployeeCollectionNewEmployee.equals(nationality)) {
                        oldNationalityOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldNationalityOfEmployeeCollectionNewEmployee = em.merge(oldNationalityOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nationality.getId();
                if (findNationality(id) == null) {
                    throw new NonexistentEntityException("The nationality with id " + id + " no longer exists.");
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
            Nationality nationality;
            try {
                nationality = em.getReference(Nationality.class, id);
                nationality.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nationality with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = nationality.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setNationality(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(nationality);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nationality> findNationalityEntities() {
        return findNationalityEntities(true, -1, -1);
    }

    public List<Nationality> findNationalityEntities(int maxResults, int firstResult) {
        return findNationalityEntities(false, maxResults, firstResult);
    }

    private List<Nationality> findNationalityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nationality.class));
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

    public Nationality findNationality(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nationality.class, id);
        } finally {
            em.close();
        }
    }

    public int getNationalityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nationality> rt = cq.from(Nationality.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
