/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.NonexistentEntityException;
import ims.dto.Computing;
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
public class ComputingJpaController implements Serializable {

    public ComputingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Computing computing) {
        if (computing.getEmployeeList() == null) {
            computing.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : computing.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            computing.setEmployeeList(attachedEmployeeList);
            em.persist(computing);
            for (Employee employeeListEmployee : computing.getEmployeeList()) {
                Computing oldComputingOfEmployeeListEmployee = employeeListEmployee.getComputing();
                employeeListEmployee.setComputing(computing);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldComputingOfEmployeeListEmployee != null) {
                    oldComputingOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldComputingOfEmployeeListEmployee = em.merge(oldComputingOfEmployeeListEmployee);
                }
            }
            em.getTransaction().commit();
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
            List<Employee> employeeListOld = persistentComputing.getEmployeeList();
            List<Employee> employeeListNew = computing.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            computing.setEmployeeList(employeeListNew);
            computing = em.merge(computing);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setComputing(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Computing oldComputingOfEmployeeListNewEmployee = employeeListNewEmployee.getComputing();
                    employeeListNewEmployee.setComputing(computing);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldComputingOfEmployeeListNewEmployee != null && !oldComputingOfEmployeeListNewEmployee.equals(computing)) {
                        oldComputingOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldComputingOfEmployeeListNewEmployee = em.merge(oldComputingOfEmployeeListNewEmployee);
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
            List<Employee> employeeList = computing.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setComputing(null);
                employeeListEmployee = em.merge(employeeListEmployee);
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
