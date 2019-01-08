/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.IllegalOrphanException;
import ims.dal.exceptions.NonexistentEntityException;
import ims.dto.Deparment;
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
public class DeparmentJpaController implements Serializable {

    public DeparmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deparment deparment) {
        if (deparment.getEmployeeCollection() == null) {
            deparment.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : deparment.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            deparment.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(deparment);
            for (Employee employeeCollectionEmployee : deparment.getEmployeeCollection()) {
                Deparment oldDeparmentOfEmployeeCollectionEmployee = employeeCollectionEmployee.getDeparment();
                employeeCollectionEmployee.setDeparment(deparment);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldDeparmentOfEmployeeCollectionEmployee != null) {
                    oldDeparmentOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldDeparmentOfEmployeeCollectionEmployee = em.merge(oldDeparmentOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deparment deparment) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deparment persistentDeparment = em.find(Deparment.class, deparment.getId());
            Collection<Employee> employeeCollectionOld = persistentDeparment.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = deparment.getEmployeeCollection();
            List<String> illegalOrphanMessages = null;
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeCollectionOldEmployee + " since its deparment field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            deparment.setEmployeeCollection(employeeCollectionNew);
            deparment = em.merge(deparment);
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Deparment oldDeparmentOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getDeparment();
                    employeeCollectionNewEmployee.setDeparment(deparment);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldDeparmentOfEmployeeCollectionNewEmployee != null && !oldDeparmentOfEmployeeCollectionNewEmployee.equals(deparment)) {
                        oldDeparmentOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldDeparmentOfEmployeeCollectionNewEmployee = em.merge(oldDeparmentOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deparment.getId();
                if (findDeparment(id) == null) {
                    throw new NonexistentEntityException("The deparment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deparment deparment;
            try {
                deparment = em.getReference(Deparment.class, id);
                deparment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deparment with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Employee> employeeCollectionOrphanCheck = deparment.getEmployeeCollection();
            for (Employee employeeCollectionOrphanCheckEmployee : employeeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deparment (" + deparment + ") cannot be destroyed since the Employee " + employeeCollectionOrphanCheckEmployee + " in its employeeCollection field has a non-nullable deparment field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(deparment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deparment> findDeparmentEntities() {
        return findDeparmentEntities(true, -1, -1);
    }

    public List<Deparment> findDeparmentEntities(int maxResults, int firstResult) {
        return findDeparmentEntities(false, maxResults, firstResult);
    }

    private List<Deparment> findDeparmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deparment.class));
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

    public Deparment findDeparment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deparment.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeparmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deparment> rt = cq.from(Deparment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
