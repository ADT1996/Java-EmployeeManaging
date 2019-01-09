/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.bll.exceptions.EmployeeException;
import ims.dal.EmployeeJpaController;
import ims.dal.exceptions.NonexistentEntityException;
import ims.dto.Employee;
import ims.util.UtilClass;
import java.util.List;

/**
 *
 * @author abc
 */
public class EmployeeBLL {
    public Employee findById(String id) {
        return new EmployeeJpaController(UtilClass.getEMF()).findEmployee(id);
    }
    
    public List<Employee> findRange(int maxResult, int beginRow) {
        return new EmployeeJpaController(UtilClass.getEMF()).findEmployeeEntities(maxResult, beginRow);
    }
    
    public List<Employee> findAll() {
        return new EmployeeJpaController(UtilClass.getEMF()).findEmployeeEntities();
    }
    
    public void edit(Employee enployee) throws Exception {
        new EmployeeJpaController(UtilClass.getEMF()).edit(enployee);
    }
    
    public void create(Employee employee) throws Exception {
        new EmployeeJpaController(UtilClass.getEMF()).create(employee);
    }
    
    public void delete(String id) throws NonexistentEntityException {
        new EmployeeJpaController(UtilClass.getEMF()).destroy(id);
    }
    
    public int count() {
        return new EmployeeJpaController(UtilClass.getEMF()).getEmployeeCount();
    }
}
