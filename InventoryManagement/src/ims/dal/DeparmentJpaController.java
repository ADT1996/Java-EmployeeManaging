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
        if (deparment.getEmployeeList() == null) {
            deparment.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : deparment.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            deparment.setEmployeeList(attachedEmployeeList);
            em.persist(deparment);
            for (Employee employeeListEmployee : deparment.getEmployeeList()) {
                Deparment oldDeparmentOfEmployeeListEmployee = employeeListEmployee.getDeparment();
                employeeListEmployee.setDeparment(deparment);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldDeparmentOfEmployeeListEmployee != null) {
                    oldDeparmentOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldDeparmentOfEmployeeListEmployee = em.merge(oldDeparmentOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentDeparment.getEmployeeList();
            List<Employee> employeeListNew = deparment.getEmployeeList();
            List<String> illegalOrphanMessages = null;
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeListOldEmployee + " since its deparment field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            deparment.setEmployeeList(employeeListNew);
            deparment = em.merge(deparment);
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Deparment oldDeparmentOfEmployeeListNewEmployee = employeeListNewEmployee.getDeparment();
                    employeeListNewEmployee.setDeparment(deparment);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldDeparmentOfEmployeeListNewEmployee != null && !oldDeparmentOfEmployeeListNewEmployee.equals(deparment)) {
                        oldDeparmentOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldDeparmentOfEmployeeListNewEmployee = em.merge(oldDeparmentOfEmployeeListNewEmployee);
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
            List<Employee> employeeListOrphanCheck = deparment.getEmployeeList();
            for (Employee employeeListOrphanCheckEmployee : employeeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deparment (" + deparment + ") cannot be destroyed since the Employee " + employeeListOrphanCheckEmployee + " in its employeeList field has a non-nullable deparment field.");
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
