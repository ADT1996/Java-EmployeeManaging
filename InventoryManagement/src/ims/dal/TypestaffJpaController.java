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
        if (typestaff.getEmployeeList() == null) {
            typestaff.setEmployeeList(new ArrayList<Employee>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employee> attachedEmployeeList = new ArrayList<Employee>();
            for (Employee employeeListEmployeeToAttach : typestaff.getEmployeeList()) {
                employeeListEmployeeToAttach = em.getReference(employeeListEmployeeToAttach.getClass(), employeeListEmployeeToAttach.getId());
                attachedEmployeeList.add(employeeListEmployeeToAttach);
            }
            typestaff.setEmployeeList(attachedEmployeeList);
            em.persist(typestaff);
            for (Employee employeeListEmployee : typestaff.getEmployeeList()) {
                Typestaff oldTypeStaffOfEmployeeListEmployee = employeeListEmployee.getTypeStaff();
                employeeListEmployee.setTypeStaff(typestaff);
                employeeListEmployee = em.merge(employeeListEmployee);
                if (oldTypeStaffOfEmployeeListEmployee != null) {
                    oldTypeStaffOfEmployeeListEmployee.getEmployeeList().remove(employeeListEmployee);
                    oldTypeStaffOfEmployeeListEmployee = em.merge(oldTypeStaffOfEmployeeListEmployee);
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
            List<Employee> employeeListOld = persistentTypestaff.getEmployeeList();
            List<Employee> employeeListNew = typestaff.getEmployeeList();
            List<String> illegalOrphanMessages = null;
            for (Employee employeeListOldEmployee : employeeListOld) {
                if (!employeeListNew.contains(employeeListOldEmployee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employee " + employeeListOldEmployee + " since its typeStaff field is not nullable.");
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
            typestaff.setEmployeeList(employeeListNew);
            typestaff = em.merge(typestaff);
            for (Employee employeeListNewEmployee : employeeListNew) {
                if (!employeeListOld.contains(employeeListNewEmployee)) {
                    Typestaff oldTypeStaffOfEmployeeListNewEmployee = employeeListNewEmployee.getTypeStaff();
                    employeeListNewEmployee.setTypeStaff(typestaff);
                    employeeListNewEmployee = em.merge(employeeListNewEmployee);
                    if (oldTypeStaffOfEmployeeListNewEmployee != null && !oldTypeStaffOfEmployeeListNewEmployee.equals(typestaff)) {
                        oldTypeStaffOfEmployeeListNewEmployee.getEmployeeList().remove(employeeListNewEmployee);
                        oldTypeStaffOfEmployeeListNewEmployee = em.merge(oldTypeStaffOfEmployeeListNewEmployee);
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
            List<Employee> employeeListOrphanCheck = typestaff.getEmployeeList();
            for (Employee employeeListOrphanCheckEmployee : employeeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Typestaff (" + typestaff + ") cannot be destroyed since the Employee " + employeeListOrphanCheckEmployee + " in its employeeList field has a non-nullable typeStaff field.");
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
