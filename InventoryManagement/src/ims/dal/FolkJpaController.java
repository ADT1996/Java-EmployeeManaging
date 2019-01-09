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
        if (folk.getEmployeeList() == null) {
            folk.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : folk.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            folk.setEmployeeList(attachedEmployeeList);
            em.persist(folk);
            for (Employee employeeListEmployee : folk.getEmployeeList()) {
                Folk oldFolkOfEmployeeListEmployee = employeeListEmployee.getFolk();
                employeeListEmployee.setFolk(folk);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldFolkOfEmployeeListEmployee != null) {
                    oldFolkOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldFolkOfEmployeeListEmployee = em.merge(oldFolkOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentFolk.getEmployeeList();
            List<Employee> employeeListNew = folk.getEmployeeList();
            List<Employee> attachedEmployeeListNew = new ArrayList<Employee>();
            for (Employee employeeListNewEmployeeToAttach : employeeListNew) {
                employeeListNewEmployeeToAttach = em.getReference(employeeListNewEmployeeToAttach.getClass(), employeeListNewEmployeeToAttach.getId());
                attachedEmployeeListNew.add(employeeListNewEmployeeToAttach);
            }
            employeeListNew = attachedEmployeeListNew;
            folk.setEmployeeList(employeeListNew);
            folk = em.merge(folk);
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    employeeListOldEmployee.setFolk(null);
                    employeeListOldEmployee = em.merge(employeeListOldEmployee);
                }
            }
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Folk oldFolkOfEmployeeListNewEmployee = employeeListNewEmployee.getFolk();
                    employeeListNewEmployee.setFolk(folk);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldFolkOfEmployeeListNewEmployee != null && !oldFolkOfEmployeeListNewEmployee.equals(folk)) {
                        oldFolkOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldFolkOfEmployeeListNewEmployee = em.merge(oldFolkOfEmployeeListNewEmployee);
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
            List<Employee> employeeList = folk.getEmployeeList();
            for (Employee employeeListEmployee : employeeList) {
                employeeListEmployee.setFolk(null);
                employeeListEmployee = em.merge(employeeListEmployee);
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
