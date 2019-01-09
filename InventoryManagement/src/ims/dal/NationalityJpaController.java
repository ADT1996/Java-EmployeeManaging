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
import ims.dto.Nationality;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abc
 */
public class NationalityJpaController implements Serializable {

    public NationalityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nationality nationality) {
        if (nationality.getEmployeeList() == null) {
            nationality.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : nationality.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            nationality.setEmployeeList(attachedEmployeeList);
            em.persist(nationality);
            for (Employee employeeListEmployee : nationality.getEmployeeList()) {
                Nationality oldNationalityOfEmployeeListEmployee = employeeListEmployee.getNationality();
                employeeListEmployee.setNationality(nationality);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldNationalityOfEmployeeListEmployee != null) {
                    oldNationalityOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldNationalityOfEmployeeListEmployee = em.merge(oldNationalityOfEmployeeListEmployee);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nationality nationality) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nationality persistentNationality = em.find(Nationality.class, nationality.getId());
            List<Employee> employeeListOld = persistentNationality.getEmployeeList();
            List<Employee> employeeListNew = nationality.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            nationality.setEmployeeList(employeeListNew);
            nationality = em.merge(nationality);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setNationality(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Nationality oldNationalityOfEmployeeListNewEmployee = employeeListNewEmployee.getNationality();
                    employeeListNewEmployee.setNationality(nationality);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldNationalityOfEmployeeListNewEmployee != null && !oldNationalityOfEmployeeListNewEmployee.equals(nationality)) {
                        oldNationalityOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldNationalityOfEmployeeListNewEmployee = em.merge(oldNationalityOfEmployeeListNewEmployee);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nationality.getId();
                if (findNationality(id) == null) {
                    throw new NonexistentEntityException("The nationality with id " + id + " no longer exists.");
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
            Nationality nationality;
            try {
                nationality = em.getReference(Nationality.class, id);
                nationality.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nationality with id " + id + " no longer exists.", enfe);
            }
            List<Employee> employeeList = nationality.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setNationality(null);
                employeeListEmployee = em.merge(employeeListEmployee);
            }
            em.remove(nationality);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nationality> findNationalityEntities() {
        return findNationalityEntities(true, -1, -1);
    }

    public List<Nationality> findNationalityEntities(int maxResults, int firstResult) {
        return findNationalityEntities(false, maxResults, firstResult);
    }

    private List<Nationality> findNationalityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nationality.class));
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

    public Nationality findNationality(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nationality.class, id);
        } finally {
            em.close();
        }
    }

    public int getNationalityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nationality> rt = cq.from(Nationality.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
