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
import ims.dto.Typestaff;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class TypestaffJpaController implements Serializable {

    public TypestaffJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Typestaff typestaff) {
        if (typestaff.getEmployeeCollection() == null) {
            typestaff.setEmployeeCollection(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Employee> attachedEmployeeCollection = new ArrayList<Employee>();
            for (Employee employeeCollectionEmployeeToAttach : typestaff.getEmployeeCollection()) {
                employeeCollectionEmployeeToAttach = em.getReference(employeeCollectionEmployeeToAttach.getClass(), employeeCollectionEmployeeToAttach.getId());
                attachedEmployeeCollection.add(employeeCollectionEmployeeToAttach);
            }
            typestaff.setEmployeeCollection(attachedEmployeeCollection);
            em.persist(typestaff);
            for (Employee employeeCollectionEmployee : typestaff.getEmployeeCollection()) {
                Typestaff oldTypeStaffOfEmployeeCollectionEmployee = employeeCollectionEmployee.getTypeStaff();
                employeeCollectionEmployee.setTypeStaff(typestaff);
                employeeCollectionEmployee = em.merge(employeeCollectionEmployee);
                if (oldTypeStaffOfEmployeeCollectionEmployee != null) {
                    oldTypeStaffOfEmployeeCollectionEmployee.getEmployeeCollection().remove(employeeCollectionEmployee);
                    oldTypeStaffOfEmployeeCollectionEmployee = em.merge(oldTypeStaffOfEmployeeCollectionEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Typestaff typestaff) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Typestaff persistentTypestaff = em.find(Typestaff.class, typestaff.getId());
            Collection<Employee> employeeCollectionOld = persistentTypestaff.getEmployeeCollection();
            Collection<Employee> employeeCollectionNew = typestaff.getEmployeeCollection();
            List<String> illegalOrphanMessages = null;
            for (Employee employeeCollectionOldEmployee : employeeCollectionOld) {
                if (!employeeCollectionNew.contains(employeeCollectionOldEmployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeCollectionOldEmployee + " since its typeStaff field is not nullable.");
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
            typestaff.setEmployeeCollection(employeeCollectionNew);
            typestaff = em.merge(typestaff);
            for (Employee employeeCollectionNewEmployee : employeeCollectionNew) {
                if (!employeeCollectionOld.contains(employeeCollectionNewEmployee)) {
                    Typestaff oldTypeStaffOfEmployeeCollectionNewEmployee = employeeCollectionNewEmployee.getTypeStaff();
                    employeeCollectionNewEmployee.setTypeStaff(typestaff);
                    employeeCollectionNewEmployee = em.merge(employeeCollectionNewEmployee);
                    if (oldTypeStaffOfEmployeeCollectionNewEmployee != null && !oldTypeStaffOfEmployeeCollectionNewEmployee.equals(typestaff)) {
                        oldTypeStaffOfEmployeeCollectionNewEmployee.getEmployeeCollection().remove(employeeCollectionNewEmployee);
                        oldTypeStaffOfEmployeeCollectionNewEmployee = em.merge(oldTypeStaffOfEmployeeCollectionNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typestaff.getId();
                if (findTypestaff(id) == null) {
                    throw new NonexistentEntityException("The typestaff with id " + id + " no longer exists.");
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
            Typestaff typestaff;
            try {
                typestaff = em.getReference(Typestaff.class, id);
                typestaff.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typestaff with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Employee> employeeCollectionOrphanCheck = typestaff.getEmployeeCollection();
            for (Employee employeeCollectionOrphanCheckEmployee : employeeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Typestaff (" + typestaff + ") cannot be destroyed since the Employee " + employeeCollectionOrphanCheckEmployee + " in its employeeCollection field has a non-nullable typeStaff field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(typestaff);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Typestaff> findTypestaffEntities() {
        return findTypestaffEntities(true, -1, -1);
    }

    public List<Typestaff> findTypestaffEntities(int maxResults, int firstResult) {
        return findTypestaffEntities(false, maxResults, firstResult);
    }

    private List<Typestaff> findTypestaffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Typestaff.class));
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

    public Typestaff findTypestaff(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Typestaff.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypestaffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Typestaff> rt = cq.from(Typestaff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
