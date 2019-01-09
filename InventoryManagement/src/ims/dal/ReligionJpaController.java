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
        if (religion.getEmployeeList() == null) {
            religion.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : religion.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            religion.setEmployeeList(attachedEmployeeList);
            em.persist(religion);
            for (Employee employeeListEmployee : religion.getEmployeeList()) {
                Religion oldReligionOfEmployeeListEmployee = employeeListEmployee.getReligion();
                employeeListEmployee.setReligion(religion);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldReligionOfEmployeeListEmployee != null) {
                    oldReligionOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldReligionOfEmployeeListEmployee = em.merge(oldReligionOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentReligion.getEmployeeList();
            List<Employee> employeeListNew = religion.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            religion.setEmployeeList(employeeListNew);
            religion = em.merge(religion);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setReligion(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Religion oldReligionOfEmployeeListNewEmployee = employeeListNewEmployee.getReligion();
                    employeeListNewEmployee.setReligion(religion);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldReligionOfEmployeeListNewEmployee != null && !oldReligionOfEmployeeListNewEmployee.equals(religion)) {
                        oldReligionOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldReligionOfEmployeeListNewEmployee = em.merge(oldReligionOfEmployeeListNewEmployee);
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
            List<Employee> employeeList = religion.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setReligion(null);
                employeeListEmployee = em.merge(employeeListEmployee);
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
