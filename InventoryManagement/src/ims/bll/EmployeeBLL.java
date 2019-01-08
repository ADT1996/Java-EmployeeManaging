/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.EmployeeJpaController;
import ims.dto.Employee;
import ims.util.UtilClass;
import java.util.List;

/**
 *
 * @author abc
 */
public class EmployeeBLL {
    public Employee findByid(String id) {
        return new EmployeeJpaController(UtilClass.getEMF()).findEmployee(id);
    }
    
    public List<Employee> findEmployeeEntities() {
        return new EmployeeJpaController(UtilClass.getEMF()).findEmployeeEntities();
    }
}
