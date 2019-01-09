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
        if (learning.getEmployeeList() == null) {
            learning.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : learning.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            learning.setEmployeeList(attachedEmployeeList);
            em.persist(learning);
            for (Employee employeeListEmployee : learning.getEmployeeList()) {
                Learning oldLearningOfEmployeeListEmployee = employeeListEmployee.getLearning();
                employeeListEmployee.setLearning(learning);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldLearningOfEmployeeListEmployee != null) {
                    oldLearningOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldLearningOfEmployeeListEmployee = em.merge(oldLearningOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentLearning.getEmployeeList();
            List<Employee> employeeListNew = learning.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            learning.setEmployeeList(employeeListNew);
            learning = em.merge(learning);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setLearning(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Learning oldLearningOfEmployeeListNewEmployee = employeeListNewEmployee.getLearning();
                    employeeListNewEmployee.setLearning(learning);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldLearningOfEmployeeListNewEmployee != null && !oldLearningOfEmployeeListNewEmployee.equals(learning)) {
                        oldLearningOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldLearningOfEmployeeListNewEmployee = em.merge(oldLearningOfEmployeeListNewEmployee);
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
            List<Employee> employeeList = learning.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setLearning(null);
                employeeListEmployee = em.merge(employeeListEmployee);
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
