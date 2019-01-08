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
import ims.dto.Foreignlanguage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class ForeignlanguageJpaController implements Serializable {

    public ForeignlanguageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foreignlanguage foreignlanguage) {
        if (foreignlanguage.getEmployeeCollection() == null) {
            foreignlanguage.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : foreignlanguage.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            foreignlanguage.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(foreignlanguage);
            for (Employee employeeCollectionEmployee : foreignlanguage.getEmployeeCollection()) {
                Foreignlanguage oldForeignLanguageOfEmployeeCollectionEmployee = employeeCollectionEmployee.getForeignLanguage();
                employeeCollectionEmployee.setForeignLanguage(foreignlanguage);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldForeignLanguageOfEmployeeCollectionEmployee != null) {
                    oldForeignLanguageOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldForeignLanguageOfEmployeeCollectionEmployee = em.merge(oldForeignLanguageOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foreignlanguage foreignlanguage) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foreignlanguage persistentForeignlanguage = em.find(Foreignlanguage.class, foreignlanguage.getId());
            Collection<Employee> employeeCollectionOld = persistentForeignlanguage.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = foreignlanguage.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            foreignlanguage.setEmployeeCollection(employeeCollectionNew);
            foreignlanguage = em.merge(foreignlanguage);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setForeignLanguage(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Foreignlanguage oldForeignLanguageOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getForeignLanguage();
                    employeeCollectionNewEmployee.setForeignLanguage(foreignlanguage);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldForeignLanguageOfEmployeeCollectionNewEmployee != null && !oldForeignLanguageOfEmployeeCollectionNewEmployee.equals(foreignlanguage)) {
                        oldForeignLanguageOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldForeignLanguageOfEmployeeCollectionNewEmployee = em.merge(oldForeignLanguageOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foreignlanguage.getId();
                if (findForeignlanguage(id) == null) {
                    throw new NonexistentEntityException("The foreignlanguage with id " + id + " no longer exists.");
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
            Foreignlanguage foreignlanguage;
            try {
                foreignlanguage = em.getReference(Foreignlanguage.class, id);
                foreignlanguage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foreignlanguage with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = foreignlanguage.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setForeignLanguage(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(foreignlanguage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foreignlanguage> findForeignlanguageEntities() {
        return findForeignlanguageEntities(true, -1, -1);
    }

    public List<Foreignlanguage> findForeignlanguageEntities(int maxResults, int firstResult) {
        return findForeignlanguageEntities(false, maxResults, firstResult);
    }

    private List<Foreignlanguage> findForeignlanguageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foreignlanguage.class));
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

    public Foreignlanguage findForeignlanguage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foreignlanguage.class, id);
        } finally {
            em.close();
        }
    }

    public int getForeignlanguageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foreignlanguage> rt = cq.from(Foreignlanguage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
