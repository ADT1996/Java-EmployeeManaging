/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dal.exceptions.NonexistentEntityException;
import ims.dto.Degree;
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
public class DegreeJpaController implements Serializable {

    public DegreeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Degree degree) {
        if (degree.getEmployeeList() == null) {
            degree.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : degree.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            degree.setEmployeeList(attachedEmployeeList);
            em.persist(degree);
            for (Employee employeeListEmployee : degree.getEmployeeList()) {
                Degree oldDegreeOfEmployeeListEmployee = employeeListEmployee.getDegree();
                employeeListEmployee.setDegree(degree);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldDegreeOfEmployeeListEmployee != null) {
                    oldDegreeOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldDegreeOfEmployeeListEmployee = em.merge(oldDegreeOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentDegree.getEmployeeList();
            List<Employee> employeeListNew = degree.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            degree.setEmployeeList(employeeListNew);
            degree = em.merge(degree);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setDegree(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Degree oldDegreeOfEmployeeListNewEmployee = employeeListNewEmployee.getDegree();
                    employeeListNewEmployee.setDegree(degree);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldDegreeOfEmployeeListNewEmployee != null && !oldDegreeOfEmployeeListNewEmployee.equals(degree)) {
                        oldDegreeOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldDegreeOfEmployeeListNewEmployee = em.merge(oldDegreeOfEmployeeListNewEmployee);
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
            List<Employee> employeeList = degree.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setDegree(null);
                employeeListEmployee = em.merge(employeeListEmployee);
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
