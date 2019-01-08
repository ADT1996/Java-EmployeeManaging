/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.bll.exceptions.EmployeeException;
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
    
    public boolean isValid(Employee employee) throws EmployeeException {
        if (employee.getId().trim().isEmpty()) {
            throw new EmployeeException("Mã nhân viên không được bỏ trống"
                    ,"Thông tin bắt buộc");
        }
        
        if (employee.getFullName().trim().isEmpty()) {
            throw new EmployeeException("Tên nhân viên không được bỏ trống"
                    ,"Thông tin bắt buộc");
        }
        
        if (employee.getBirthDay() != null || !employee.getBirthPlace().trim().isEmpty()) {
            if (employee.getBirthPlace().trim().isEmpty()) {
                throw new EmployeeException("Nơi sinh không được bỏ trống"
                        ,"Ngày sinh đã được nhập");
            }
            if (employee.getBirthDay() != null) {
                throw new EmployeeException("Ngày sinh không được bỏ trống"
                        ,"Nơi sinh đã được nhập");
            }
        }
        
        if (employee.getPersonCode().trim().isEmpty()) {
            if(employee.getTakenPCPlace().trim().isEmpty()) {
                throw new EmployeeException("Nơi cấp CMND không được bở trống"
                        ,"Số CMND đã được nhập");
            }
            
            if(employee.getTakenPCDate()== null) {
                throw new EmployeeException("Ngày cấp CMND không được bỏ trống"
                        ,"Số CMND đã được nhập");
            }
        }
        
        if (employee.getTakenPCDate()!= null) {
            if(employee.getTakenPCPlace().trim().isEmpty()) {
                throw new EmployeeException("Nơi cấp CMND không được bở trống"
                        ,"Ngày cấp CMND đã được nhập");
            }
            
            if(employee.getPersonCode()== null) {
                throw new EmployeeException("Số CMND không được bỏ trống"
                        ,"Ngày cấp CMND đã được nhập");
            }
        }
        
        if (!employee.getTakenPCPlace().trim().isEmpty()) {
            if(employee.getTakenPCDate()==null) {
                throw new EmployeeException("Ngày cấp CMND không được bở trống"
                        ,"Nơi cấp CMND đã được nhập");
            }
            
            if(employee.getPersonCode()== null) {
                throw new EmployeeException("Số CMND không được bỏ trống"
                        ,"Nơi cấp CMND đã được nhập");
            }
        }
        
        if (employee.getTypeStaff() == null) {
            throw new EmployeeException("Chưa chọn loại nhân viên"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getStartDate() == null) {
            throw new EmployeeException("Chưa nhập ngày vào làm"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getDeparment() == null) {
            throw new EmployeeException("Chưa chọn phòng ban"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getJob()== null) {
            throw new EmployeeException("Chưa chọn chọn công việc"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getPosition()== null) {
            throw new EmployeeException("Chưa chọn chức vụ"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getPosition()== null) {
            throw new EmployeeException("Chưa chọn chức vụ"
                        ,"Thông tin bắt buộc");
        }
        
        if (employee.getLaborCode() != null) {
            if (employee.getTakenLaborDate() == null) {
                throw new EmployeeException("Chưa nhập ngày lấy sổ lao động"
                        ,"Số sổ lao động đã được nhập");
            }
            
            if (employee.getTakenLaborPlace().trim().isEmpty()) {
                throw new EmployeeException("Chưa nhập nơi lấy sổ lao động"
                        ,"Số sổ lao động đã được nhập");
            }
        }
        
        if (employee.getTakenLaborDate()!= null) {
            if (employee.getLaborCode() == null) {
                throw new EmployeeException("Chưa nhập số sổ lao động"
                        ,"Ngày lấy sổ đã được nhập");
            }
            
            if (employee.getTakenLaborPlace().trim().isEmpty()) {
                throw new EmployeeException("Chưa nhập nơi lấy sổ lao động"
                        ,"Ngày lấy sổ đã được nhập");
            }
        }
        
        if (employee.getTakenLaborPlace().trim().isEmpty()) {
            if (employee.getTakenLaborDate() == null) {
                throw new EmployeeException("Chưa nhập ngày lấy sổ lao động"
                        ,"Nơi lấy sổ đã được nhập");
            }
            
            if (employee.getLaborCode() == null) {
                throw new EmployeeException("Chưa nhập số sổ sổ lao động"
                        ,"Nơi lấy sổ đã được nhập");
            }
        }
        
        
        
        return true;
    }
}
