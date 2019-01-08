/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.IllegalOrphanException;
import ims.dal.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Employee;
import ims.dto.EmployeePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class EmployeePositionJpaController implements Serializable {

    public EmployeePositionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmployeePosition employeePosition) {
        if (employeePosition.getEmployeeCollection() == null) {
            employeePosition.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : employeePosition.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            employeePosition.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(employeePosition);
            for (Employee employeeCollectionEmployee : employeePosition.getEmployeeCollection()) {
                EmployeePosition oldPositionOfEmployeeCollectionEmployee = employeeCollectionEmployee.getPosition();
                employeeCollectionEmployee.setPosition(employeePosition);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldPositionOfEmployeeCollectionEmployee != null) {
                    oldPositionOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldPositionOfEmployeeCollectionEmployee = em.merge(oldPositionOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmployeePosition employeePosition) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmployeePosition persistentEmployeePosition = em.find(EmployeePosition.class, employeePosition.getId());
            Collection<Employee> employeeCollectionOld = persistentEmployeePosition.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = employeePosition.getEmployeeCollection();
            List<String> illegalOrphanMessages = null;
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeCollectionOldEmployee + " since its position field is not nullable.");
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
            employeePosition.setEmployeeCollection(employeeCollectionNew);
            employeePosition = em.merge(employeePosition);
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    EmployeePosition oldPositionOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getPosition();
                    employeeCollectionNewEmployee.setPosition(employeePosition);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldPositionOfEmployeeCollectionNewEmployee != null && !oldPositionOfEmployeeCollectionNewEmployee.equals(employeePosition)) {
                        oldPositionOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldPositionOfEmployeeCollectionNewEmployee = em.merge(oldPositionOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employeePosition.getId();
                if (findEmployeePosition(id) == null) {
                    throw new NonexistentEntityException("The employeePosition with id " + id + " no longer exists.");
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
            EmployeePosition employeePosition;
            try {
                employeePosition = em.getReference(EmployeePosition.class, id);
                employeePosition.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employeePosition with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Employee> employeeCollectionOrphanCheck = employeePosition.getEmployeeCollection();
            for (Employee employeeCollectionOrphanCheckEmployee : employeeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmployeePosition (" + employeePosition + ") cannot be destroyed since the Employee " + employeeCollectionOrphanCheckEmployee + " in its employeeCollection field has a non-nullable position field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employeePosition);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmployeePosition> findEmployeePositionEntities() {
        return findEmployeePositionEntities(true, -1, -1);
    }

    public List<EmployeePosition> findEmployeePositionEntities(int maxResults, int firstResult) {
        return findEmployeePositionEntities(false, maxResults, firstResult);
    }

    private List<EmployeePosition> findEmployeePositionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmployeePosition.class));
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

    public EmployeePosition findEmployeePosition(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmployeePosition.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeePositionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmployeePosition> rt = cq.from(EmployeePosition.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
