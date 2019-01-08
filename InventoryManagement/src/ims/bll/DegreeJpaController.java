/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.bll.exceptions.NonexistentEntityException;
import ims.dto.Degree;
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
public class DegreeJpaController implements Serializable {

    public DegreeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Degree degree) {
        if (degree.getEmployeeCollection() == null) {
            degree.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : degree.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            degree.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(degree);
            for (Employee employeeCollectionEmployee : degree.getEmployeeCollection()) {
                Degree oldDegreeOfEmployeeCollectionEmployee = employeeCollectionEmployee.getDegree();
                employeeCollectionEmployee.setDegree(degree);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldDegreeOfEmployeeCollectionEmployee != null) {
                    oldDegreeOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldDegreeOfEmployeeCollectionEmployee = em.merge(oldDegreeOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Degree degree) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Degree persistentDegree = em.find(Degree.class, degree.getId());
            Collection<Employee> employeeCollectionOld = persistentDegree.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = degree.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            degree.setEmployeeCollection(employeeCollectionNew);
            degree = em.merge(degree);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setDegree(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Degree oldDegreeOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getDegree();
                    employeeCollectionNewEmployee.setDegree(degree);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldDegreeOfEmployeeCollectionNewEmployee != null && !oldDegreeOfEmployeeCollectionNewEmployee.equals(degree)) {
                        oldDegreeOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldDegreeOfEmployeeCollectionNewEmployee = em.merge(oldDegreeOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = degree.getId();
                if (findDegree(id) == null) {
                    throw new NonexistentEntityException("The degree with id " + id + " no longer exists.");
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
            Degree degree;
            try {
                degree = em.getReference(Degree.class, id);
                degree.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The degree with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = degree.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setDegree(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(degree);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Degree> findDegreeEntities() {
        return findDegreeEntities(true, -1, -1);
    }

    public List<Degree> findDegreeEntities(int maxResults, int firstResult) {
        return findDegreeEntities(false, maxResults, firstResult);
    }

    private List<Degree> findDegreeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Degree.class));
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

    public Degree findDegree(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Degree.class, id);
        } finally {
            em.close();
        }
    }

    public int getDegreeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Degree> rt = cq.from(Degree.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
