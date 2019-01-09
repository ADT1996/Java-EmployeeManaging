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
import ims.dto.Foreignlanguage;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class ForeignlanguageJpaController implements Serializable {

    public ForeignlanguageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foreignlanguage foreignlanguage) {
        if (foreignlanguage.getEmployeeList() == null) {
            foreignlanguage.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : foreignlanguage.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            foreignlanguage.setEmployeeList(attachedEmployeeList);
            em.persist(foreignlanguage);
            for (Employee employeeListEmployee : foreignlanguage.getEmployeeList()) {
                Foreignlanguage oldForeignLanguageOfEmployeeListEmployee = employeeListEmployee.getForeignLanguage();
                employeeListEmployee.setForeignLanguage(foreignlanguage);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldForeignLanguageOfEmployeeListEmployee != null) {
                    oldForeignLanguageOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldForeignLanguageOfEmployeeListEmployee = em.merge(oldForeignLanguageOfEmployeeListEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foreignlanguage foreignlanguage) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foreignlanguage persistentForeignlanguage = em.find(Foreignlanguage.class, foreignlanguage.getId());
            List<Employee> employeeListOld = persistentForeignlanguage.getEmployeeList();
            List<Employee> employeeListNew = foreignlanguage.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            foreignlanguage.setEmployeeList(employeeListNew);
            foreignlanguage = em.merge(foreignlanguage);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setForeignLanguage(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Foreignlanguage oldForeignLanguageOfEmployeeListNewEmployee = employeeListNewEmployee.getForeignLanguage();
                    employeeListNewEmployee.setForeignLanguage(foreignlanguage);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldForeignLanguageOfEmployeeListNewEmployee != null && !oldForeignLanguageOfEmployeeListNewEmployee.equals(foreignlanguage)) {
                        oldForeignLanguageOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldForeignLanguageOfEmployeeListNewEmployee = em.merge(oldForeignLanguageOfEmployeeListNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foreignlanguage.getId();
                if (findForeignlanguage(id) == null) {
                    throw new NonexistentEntityException("The foreignlanguage with id " + id + " no longer exists.");
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
            Foreignlanguage foreignlanguage;
            try {
                foreignlanguage = em.getReference(Foreignlanguage.class, id);
                foreignlanguage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foreignlanguage with id " + id + " no longer exists.", enfe);
            }
            List<Employee> employeeList = foreignlanguage.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setForeignLanguage(null);
                employeeListEmployee = em.merge(employeeListEmployee);
            }
            em.remove(foreignlanguage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foreignlanguage> findForeignlanguageEntities() {
        return findForeignlanguageEntities(true, -1, -1);
    }

    public List<Foreignlanguage> findForeignlanguageEntities(int maxResults, int firstResult) {
        return findForeignlanguageEntities(false, maxResults, firstResult);
    }

    private List<Foreignlanguage> findForeignlanguageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foreignlanguage.class));
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

    public Foreignlanguage findForeignlanguage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foreignlanguage.class, id);
        } finally {
            em.close();
        }
    }

    public int getForeignlanguageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foreignlanguage> rt = cq.from(Foreignlanguage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
