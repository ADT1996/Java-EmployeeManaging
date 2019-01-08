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
import ims.dto.Religion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class ReligionJpaController implements Serializable {

    public ReligionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Religion religion) {
        if (religion.getEmployeeCollection() == null) {
            religion.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : religion.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            religion.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(religion);
            for (Employee employeeCollectionEmployee : religion.getEmployeeCollection()) {
                Religion oldReligionOfEmployeeCollectionEmployee = employeeCollectionEmployee.getReligion();
                employeeCollectionEmployee.setReligion(religion);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldReligionOfEmployeeCollectionEmployee != null) {
                    oldReligionOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldReligionOfEmployeeCollectionEmployee = em.merge(oldReligionOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Religion religion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Religion persistentReligion = em.find(Religion.class, religion.getId());
            Collection<Employee> employeeCollectionOld = persistentReligion.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = religion.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            religion.setEmployeeCollection(employeeCollectionNew);
            religion = em.merge(religion);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setReligion(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Religion oldReligionOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getReligion();
                    employeeCollectionNewEmployee.setReligion(religion);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldReligionOfEmployeeCollectionNewEmployee != null && !oldReligionOfEmployeeCollectionNewEmployee.equals(religion)) {
                        oldReligionOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldReligionOfEmployeeCollectionNewEmployee = em.merge(oldReligionOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = religion.getId();
                if (findReligion(id) == null) {
                    throw new NonexistentEntityException("The religion with id " + id + " no longer exists.");
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
            Religion religion;
            try {
                religion = em.getReference(Religion.class, id);
                religion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The religion with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = religion.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setReligion(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(religion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Religion> findReligionEntities() {
        return findReligionEntities(true, -1, -1);
    }

    public List<Religion> findReligionEntities(int maxResults, int firstResult) {
        return findReligionEntities(false, maxResults, firstResult);
    }

    private List<Religion> findReligionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Religion.class));
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

    public Religion findReligion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Religion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReligionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Religion> rt = cq.from(Religion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
