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
import ims.dto.Learning;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class LearningJpaController implements Serializable {

    public LearningJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Learning learning) {
        if (learning.getEmployeeCollection() == null) {
            learning.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : learning.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            learning.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(learning);
            for (Employee employeeCollectionEmployee : learning.getEmployeeCollection()) {
                Learning oldLearningOfEmployeeCollectionEmployee = employeeCollectionEmployee.getLearning();
                employeeCollectionEmployee.setLearning(learning);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldLearningOfEmployeeCollectionEmployee != null) {
                    oldLearningOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldLearningOfEmployeeCollectionEmployee = em.merge(oldLearningOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Learning learning) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Learning persistentLearning = em.find(Learning.class, learning.getId());
            Collection<Employee> employeeCollectionOld = persistentLearning.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = learning.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            learning.setEmployeeCollection(employeeCollectionNew);
            learning = em.merge(learning);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setLearning(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Learning oldLearningOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getLearning();
                    employeeCollectionNewEmployee.setLearning(learning);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldLearningOfEmployeeCollectionNewEmployee != null && !oldLearningOfEmployeeCollectionNewEmployee.equals(learning)) {
                        oldLearningOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldLearningOfEmployeeCollectionNewEmployee = em.merge(oldLearningOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = learning.getId();
                if (findLearning(id) == null) {
                    throw new NonexistentEntityException("The learning with id " + id + " no longer exists.");
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
            Learning learning;
            try {
                learning = em.getReference(Learning.class, id);
                learning.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The learning with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = learning.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setLearning(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(learning);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Learning> findLearningEntities() {
        return findLearningEntities(true, -1, -1);
    }

    public List<Learning> findLearningEntities(int maxResults, int firstResult) {
        return findLearningEntities(false, maxResults, firstResult);
    }

    private List<Learning> findLearningEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Learning.class));
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

    public Learning findLearning(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Learning.class, id);
        } finally {
            em.close();
        }
    }

    public int getLearningCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Learning> rt = cq.from(Learning.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
