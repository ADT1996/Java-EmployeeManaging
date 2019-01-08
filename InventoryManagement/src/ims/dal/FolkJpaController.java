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
import ims.dto.Folk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class FolkJpaController implements Serializable {

    public FolkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Folk folk) {
        if (folk.getEmployeeCollection() == null) {
            folk.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : folk.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            folk.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(folk);
            for (Employee employeeCollectionEmployee : folk.getEmployeeCollection()) {
                Folk oldFolkOfEmployeeCollectionEmployee = employeeCollectionEmployee.getFolk();
                employeeCollectionEmployee.setFolk(folk);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldFolkOfEmployeeCollectionEmployee != null) {
                    oldFolkOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldFolkOfEmployeeCollectionEmployee = em.merge(oldFolkOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Folk folk) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Folk persistentFolk = em.find(Folk.class, folk.getId());
            Collection<Employee> employeeCollectionOld = persistentFolk.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = folk.getEmployeeCollection();
            Collection<Employee> attachedEmployeeCollectionNew = new ArrayList<Employee>();
            for (Employee employeeCollectionNewEmployeeToAttach : employeeCollectionNew) {
                employeeCollectionNewEmployeeToAttach = em.getReference(employeeCollectionNewEmployeeToAttach.getClass(), employeeCollectionNewEmployeeToAttach.getId());
                attachedEmployeeCollectionNew.add(employeeCollectionNewEmployeeToAttach);
            }
            employeeCollectionNew = attachedEmployeeCollectionNew;
            folk.setEmployeeCollection(employeeCollectionNew);
            folk = em.merge(folk);
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    employeeCollectionOldEmployee.setFolk(null);
                    employeeCollectionOldEmployee = em.merge(employeeCollectionOldEmployee);
                }
            }
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Folk oldFolkOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getFolk();
                    employeeCollectionNewEmployee.setFolk(folk);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldFolkOfEmployeeCollectionNewEmployee != null && !oldFolkOfEmployeeCollectionNewEmployee.equals(folk)) {
                        oldFolkOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldFolkOfEmployeeCollectionNewEmployee = em.merge(oldFolkOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = folk.getId();
                if (findFolk(id) == null) {
                    throw new NonexistentEntityException("The folk with id " + id + " no longer exists.");
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
            Folk folk;
            try {
                folk = em.getReference(Folk.class, id);
                folk.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The folk with id " + id + " no longer exists.", enfe);
            }
            Collection<Employee> employeeCollection = folk.getEmployeeCollection();
            for (Employee employeeCollectionEmployee : employeeCollection) {
                employeeCollectionEmployee.setFolk(null);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
            }
            em.remove(folk);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Folk> findFolkEntities() {
        return findFolkEntities(true, -1, -1);
    }

    public List<Folk> findFolkEntities(int maxResults, int firstResult) {
        return findFolkEntities(false, maxResults, firstResult);
    }

    private List<Folk> findFolkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Folk.class));
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

    public Folk findFolk(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Folk.class, id);
        } finally {
            em.close();
        }
    }

    public int getFolkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Folk> rt = cq.from(Folk.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
