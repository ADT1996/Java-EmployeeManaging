/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.bll.exceptions.NonexistentEntityException;
import ims.bll.exceptions.PreexistingEntityException;
import ims.dto.Computing;
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
public class ComputingJpaController implements Serializable {

    public ComputingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Computing computing) throws PreexistingEntityException, Exception {
        if (computing.getEmployeeCollection() == null) {
            computing.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : computing.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            computing.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(computing);
            for (Employee employeeCollectionEmployee : computing.getEmployeeCollection()) {
                Computing oldComputingOfEmployeeCollectionEmployee = employeeCollectionEmployee.getComputing();
                employeeCollectionEmployee.setComputing(computing);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldComputingOfEmployeeCollectionEmployee != null) {
                    oldComputingOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldComputingOfEmployeeCollectionEmployee = em.merge(oldComputingOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComputing(computing.getId()) != null) {
                throw new PreexistingEntityException("Computing " + computing + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Computing computing) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Computing persistentComputing = em.find(Computing.class, computing.getId());
            Collection<Employee> employeeCollectionOld = persistentComputing.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = computing.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            computing.setEmployeeCollection(employeeCollectionNew);
            computing = em.merge(computing);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setComputing(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Computing oldComputingOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getComputing();
                    employeeCollectionNewEmployee.setComputing(computing);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldComputingOfEmployeeCollectionNewEmployee != null && !oldComputingOfEmployeeCollectionNewEmployee.equals(computing)) {
                        oldComputingOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldComputingOfEmployeeCollectionNewEmployee = em.merge(oldComputingOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = computing.getId();
                if (findComputing(id) == null) {
                    throw new NonexistentEntityException("The computing with id " + id + " no longer exists.");
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
            Computing computing;
            try {
                computing = em.getReference(Computing.class, id);
                computing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The computing with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = computing.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setComputing(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(computing);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Computing> findComputingEntities() {
        return findComputingEntities(true, -1, -1);
    }

    public List<Computing> findComputingEntities(int maxResults, int firstResult) {
        return findComputingEntities(false, maxResults, firstResult);
    }

    private List<Computing> findComputingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Computing.class));
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

    public Computing findComputing(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Computing.class, id);
        } finally {
            em.close();
        }
    }

    public int getComputingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Computing> rt = cq.from(Computing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
